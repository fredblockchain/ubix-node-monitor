package com.fred.node.chain.data;

import com.fred.node.chain.model.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionService {
    private ArrayList<Transaction> transactions;
    private String lastTransaction;

    public TransactionService() {
        this.transactions = new ArrayList<Transaction>();
    }
    public void saveTransaction(Transaction t) {
        if(!transactions.contains(t)) {
           transactions.add(0, t);
        }
    }

    public void setLastTransaction(String lastTransaction) {
        this.lastTransaction = lastTransaction;
    }

    public String getLastTransaction() {
        return this.lastTransaction;
    }

    public ArrayList<Transaction> getTransactionsForDay(LocalDate d) {
        ArrayList<Transaction> res = new ArrayList<>();
        if(this.transactions != null) {
            for (Transaction t : this.transactions) {
                if(t.getDate().toLocalDate().equals(d) )
                    res.add(t);
            }
        }
        return res;
    }
}
