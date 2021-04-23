package com.fred.node.monitor.price.coinmarketcap.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Quote {


    private double price;

    @JsonAnySetter
    public void setFiatData(String key, Object value) throws JsonProcessingException {
        java.util.LinkedHashMap tmp = (java.util.LinkedHashMap) value;
        this.price = (Double) tmp.get("price");
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
