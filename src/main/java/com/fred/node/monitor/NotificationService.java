package com.fred.node.monitor;

import com.fred.node.chain.model.Transaction;
import com.fred.node.monitor.model.SyncStatus;
import com.fred.node.ubix.transactions.sync.TransactionWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;


public class NotificationService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("telegramBot")
    public TelegramMonitorBot telegramBot;

    public void notifySyncStatus(String nodeID){
        this.telegramBot.notifyStatus(nodeID);
    }

    public void notifyTransactions(ArrayList<Transaction> transactions) {
        telegramBot.notifyTransactions(transactions);
    }
}
