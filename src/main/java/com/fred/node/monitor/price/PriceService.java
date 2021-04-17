package com.fred.node.monitor.price;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fred.node.monitor.TelegramMonitorBot;
import com.fred.node.monitor.price.coinmarketcap.model.*;
import com.fred.node.ubix.transactions.sync.UBIXTransactionSync;
import com.fred.node.ubix.transactions.sync.ubixapi.TransactionPage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;


@Service
public class PriceService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=UBX";
    private static String APIkey = "";

    private double price = 0.0;

    public PriceService(@Value("${price.coinmarketcap.apikey}")String apiKey) {
        this.APIkey = apiKey;
    }

    public double getPrice() {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().
                url(url).
                addHeader("X-CMC_PRO_API_KEY",this.APIkey).build(); // defaults to GET

        try {
            Response response = client.newCall(request).execute();
            log.info("response code: "+ response.code());
            //log.info(response.body().string());
            //log.info(response.message());
            QuoteLatestResponse responseQuoteLatest = mapper.readValue(response.body().byteStream(), QuoteLatestResponse.class);
            Data data = responseQuoteLatest.getData();
            TokenData one  = data.getOne();
            Quote quote = one.getQuote();
            FiatData usd = quote.getUsd();

            log.info("Price is: "+ usd.getPrice());
            this.price = usd.getPrice();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.price;
    }

    }
