package com.fred.node.monitor.price;

public class TokenPrice {
    private double price;
    private String currencyIsoCode;

    public TokenPrice(double price, String currencyIsoCode) {
        this.price = price;
        this.currencyIsoCode = currencyIsoCode;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrencyIsoCode() {
        return currencyIsoCode;
    }

    public void setCurrencyIsoCode(String currencyIsoCode) {
        this.currencyIsoCode = currencyIsoCode;
    }
}
