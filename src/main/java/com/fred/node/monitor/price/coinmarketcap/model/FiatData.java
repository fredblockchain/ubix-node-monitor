package com.fred.node.monitor.price.coinmarketcap.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FiatData {
    private final double price;

    public FiatData(@JsonProperty("price") double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
