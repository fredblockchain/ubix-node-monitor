package com.fred.node.ubix.transactions.sync;

import org.springframework.stereotype.Component;

@Component("lastTransaction")
public class LastTransaction {

    private String lastTransaction = "";

    public LastTransaction() {
        if(this.lastTransaction == "") {
            this.lastTransaction = "la que pillamos de la BDD";
        }
    }

    public void updateLastTransaction(String newID) {
        this.lastTransaction = newID;
        // y guardar en BDD
    }
}
