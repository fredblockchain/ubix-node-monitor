package com.fred.node.chain.model;


import java.time.LocalDateTime;

public class Transaction {
    public final static String SELF = "self";

    private final String chain;
    private final String id;
    private final String address;
    private final String fromAddr;
    private final String toAddr;
    private final double value;
    private final LocalDateTime date;
    private final double fiatValue;
    private final String fiatID;

    public Transaction(String chain, String address, String id, String fromAddr, String toAddr, double value, LocalDateTime date, double fiatValue, String fiatID) {
        this.chain = chain;
        this.address = address;
        this.id = id;
        this.fromAddr = fromAddr;
        this.toAddr = toAddr;
        this.value = value;
        this.date = date;
        this.fiatValue = fiatValue;
        this.fiatID = fiatID;
    }

    public String getAddress() {
        return address;
    }

    public String getChain() {
        return chain;
    }

    public String getId() {
        return id;
    }

    public String getFromAddr() {
        return fromAddr;
    }

    public String getToAddr() {
        return toAddr;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object transaction) {
        if(transaction instanceof Transaction &&
                ((Transaction) transaction).getId().equals(this.id)
        )
            return true;
        else
            return false;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getFiatValue() {
        return fiatValue;
    }

    public String getFiatID() {
        return fiatID;
    }
}
