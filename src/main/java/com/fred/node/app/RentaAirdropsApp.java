package com.fred.node.app;


import com.fred.node.chain.model.Transaction;
import com.fred.node.ubix.transactions.sync.UBIXTransactionSync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.ArrayList;

@SpringBootApplication
@ComponentScan("com.fred")

public class RentaAirdropsApp  implements ApplicationListener<ApplicationReadyEvent> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UBIXTransactionSync ubixTransactionSync;

    public void onApplicationEvent(ApplicationReadyEvent event) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            transactions = ubixTransactionSync.getAllTransactions("c9d6fed9f17cb5aa28332b21dd37e0ff1a6b48d6");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main_(String[] args) {

        SpringApplication.run(RentaAirdropsApp.class);


    }
}
