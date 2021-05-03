package com.fred.node.ubix.transactions.sync;

import java.io.IOException;
import java.util.ArrayList;


import com.fred.node.chain.StatusService;
import com.fred.node.chain.data.TransactionService;
import com.fred.node.chain.model.Transaction;
import com.fred.node.monitor.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TransactionWatcher {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("statusService")
    private StatusService statusService;

    @Autowired
    @Qualifier("notificationService")
    public NotificationService notificationService;

    @Autowired
    @Qualifier("transactionService")
    private TransactionService transactionService;

    @Autowired
    private UBIXTransactionSync ubixTransactionSync;

    private String address;

    private String lastTransaction;


    public TransactionWatcher(@Value("${node.ubix.address}") String address) {
        this.address = address;
        //this.address = "c9d6fed9f17cb5aa28332b21dd37e0ff1a6b48d6";
        //ubixTransactionSync = new UBIXTransactionSync();
    }

    @Scheduled(fixedDelay = 900000)
    public void updateTransactions (){
        if(!this.statusService.isKnownNode("UBX"))
            this.statusService.addNode("UBX", "Ubix");

        log.info("start transaction check...");
        try {
            this.checkNewTransactions();
        } catch (Exception e) {
            log.error("error while checking transactions",e);
            this.statusService.setFailed("UBX", e.getMessage());
        }
        log.info("UBIXTransaction check done");
    }

    private void checkNewTransactions() throws IOException {

        this.statusService.setRunning("UBX","synchronization started");

        ArrayList<Transaction> transactions = new ArrayList<>();

        if(this.transactionService.getLastTransaction() == null) {
            log.info("search for transactions for the first time");
            notificationService.notifySyncStatus("UBX");

            transactions = ubixTransactionSync.getAllTransactions(this.address);
            this.statusService.setSynched("UBX");

            notificationService.notifySyncStatus("UBX");

            this.saveTransactions(transactions);
        } else {
            log.info("last transaction: "+this.transactionService.getLastTransaction());
            transactions = ubixTransactionSync.getTransactionsUntil(this.address, this.transactionService.getLastTransaction());
            this.saveTransactions(transactions);
            this.statusService.setSynched("UBX");

            if(transactions.size()>0)
                this.notificationService.notifyTransactions(transactions);
        }



    }

    private void saveTransactions(ArrayList<Transaction> transactions) {
        if(transactions.size() > 0) {
            log.info("found "+transactions.size() + " new transactions");
            for(Transaction t: transactions) {
                this.transactionService.saveTransaction(t);
            }

            this.transactionService.setLastTransaction(transactions.get(0).getId());
            this.statusService.setSynched("UBX");


        } else {
            log.info("no new transactions");
        }
    }


}
