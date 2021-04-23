package com.fred.node.monitor.price.coinmarketcap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fred.node.monitor.price.coinmarketcap.model.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CoinMarketCap {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static String APIkey = "";

    public CoinMarketCap(String apiKey) {
        this.APIkey = apiKey;
    }

    private final String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=";

    public String callAPIforToken(String token, String currencyIsoCode) throws IOException {
        String url = this.url + token;
        url += "&convert="+currencyIsoCode;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().
                url(url).
                addHeader("X-CMC_PRO_API_KEY",this.APIkey).build(); // defaults to GET

        Response response = client.newCall(request).execute();
        log.info("response code: "+ response.code());

        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (response.body().byteStream(), Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }

        return textBuilder.toString();

    }

    public double extractPrice(String json) throws JsonProcessingException {
       ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        QuoteLatestResponse responseQuoteLatest = mapper.readValue(json, QuoteLatestResponse.class);
        Data data = responseQuoteLatest.getData();
        TokenData one  = data.getOne();
        Quote quote = one.getQuote();

        return quote.getPrice();
    }
}
