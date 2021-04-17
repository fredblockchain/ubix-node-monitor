package com.fred.node.ubix.transactions.sync;

import com.fred.node.chain.StatusService;
import com.fred.node.chain.model.Transaction;
import com.fred.node.ubix.transactions.sync.ubixapi.UBIXTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;



@Component
public class UBIXTransactionSync {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private String lastKnownTransactionID;


    @Autowired
    @Qualifier("statusService")
    private StatusService statusService;

    public void setLastKnownTransaction(String transactionID) {
        this.lastKnownTransactionID = transactionID;
    }

    public UBIXTransactionSync() {

    }

    ArrayList<Transaction> getTransactionsUntil(String address, String lastKnownTransactionID) {
        // TODO make calls in parallel:
        // https://spring.io/guides/gs/async-method/

        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        boolean keepLoading = true;
        int pageNb = 0;

        //log.info("last transaction was: "+lastKnownTransactionID);
        while (keepLoading) {
            log.info("retrieve page " + pageNb);
            UBIXTransactionPage currentPage = new UBIXTransactionPage(address, pageNb);
            try {
                if(this.statusService !=null)
                    this.statusService.updateSyncInfo("synching page "+ pageNb);
                else
                    log.info("statusService Object is null");
                currentPage.synchronize();

                for (UBIXTransaction t : currentPage.getTransactions()) {
                    if (lastKnownTransactionID != null && lastKnownTransactionID.equals(t.getId())) {
                        keepLoading = false;
                    }
                    if(keepLoading) {
                        transactions.add(this.convertTransaction(t, address) );
                    }
                }

                if (currentPage.getTransactions().size() == 0) keepLoading = false;
                pageNb++;
                //if (pageNb > 0) keepLoading = false; // test purpose
                //wait(10000,0);
            } catch (IOException e) {
                e.printStackTrace();
                keepLoading = false;
            }
        }
        return transactions;
    }

    public ArrayList<Transaction> getAllTransactions(String address) {
        return this.getTransactionsUntil(address,null);
    }

    private com.fred.node.chain.model.Transaction convertTransaction(UBIXTransaction tSrc, String address) {

        return new com.fred.node.chain.model.Transaction(
                "UBIX",
                address,
                tSrc.getId(),
                tSrc.getInputs().get(0).getFrom(),
                address,
                tSrc.getValue(),
                LocalDateTime.ofEpochSecond(tSrc.getTimestamp(),0, ZoneOffset.UTC) );
    }
}
