package com.fred.node.price.coinmarketcap.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

    private final TokenData ubx;

    public Data(@JsonProperty("UBX") TokenData ubx) {
        this.ubx = ubx;
    }

    public TokenData getOne() {
        return ubx;
    }
}
