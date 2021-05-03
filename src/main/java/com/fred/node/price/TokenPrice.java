package com.fred.node.price;

import java.time.LocalDateTime;

public class TokenPrice {
    private String token;
    private double price;
    private String currencyIsoCode;
    private LocalDateTime priceTime;

    public TokenPrice(String token, double price, String currencyIsoCode, LocalDateTime priceTime) {
        this.token = token;
        this.price = price;
        this.currencyIsoCode = currencyIsoCode;
        this.priceTime = priceTime;
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

    public LocalDateTime getPriceTime() {
        return priceTime;
    }

    public void setPriceTime(LocalDateTime priceTime) {
        this.priceTime = priceTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
