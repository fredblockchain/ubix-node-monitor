package com.fred.node.price.coinmarketcap.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenData {

    private final Quote quote;

    public TokenData(@JsonProperty("quote") Quote quote) {
        this.quote = quote;
    }

    public Quote getQuote() {
        return quote;
    }
}
