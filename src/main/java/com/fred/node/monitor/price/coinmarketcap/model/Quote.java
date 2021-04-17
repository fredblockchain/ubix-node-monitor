package com.fred.node.monitor.price.coinmarketcap.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Quote {
    private final FiatData usd;

    public Quote(@JsonProperty("USD") FiatData usd) {
        this.usd = usd;
    }

    public FiatData getUsd() {
        return usd;
    }
}
