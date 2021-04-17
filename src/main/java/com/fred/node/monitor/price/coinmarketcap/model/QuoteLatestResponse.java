package com.fred.node.monitor.price.coinmarketcap.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuoteLatestResponse {
    private final Data data;

    public QuoteLatestResponse(@JsonProperty("data")Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }
}
