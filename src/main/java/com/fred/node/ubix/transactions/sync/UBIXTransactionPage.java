package com.fred.node.ubix.transactions.sync;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fred.node.ubix.transactions.sync.ubixapi.TransactionPage;
import com.fred.node.ubix.transactions.sync.ubixapi.UBIXTransaction;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;

public class UBIXTransactionPage {
    private static String BASE_URL = "https://explorer.ubikiri.com/api/address/";

    private long pageNb;
    private String address;

    private TransactionPage apiPage;

    public UBIXTransactionPage(String address, long pageNb) {
        this.address = address; this.pageNb = pageNb;
    }

    public int synchronize() throws IOException {
        String pageUrl = BASE_URL + this.address + "?page=" + this.pageNb;
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(pageUrl).build(); // defaults to GET
        Response response = client.newCall(request).execute();
        this.apiPage = mapper.readValue(response.body().byteStream(), TransactionPage.class);

        return this.apiPage.txInfoDTOs.size();
    }

    public long getLength() {
        return this.apiPage.txInfoDTOs.size();
    }

    public ArrayList<UBIXTransaction> getTransactions() {
        return this.apiPage.txInfoDTOs;
    }

    public ArrayList<UBIXTransaction> getTransactionsUntil(String transactionID) {

        return this.apiPage.txInfoDTOs;
    }

}
