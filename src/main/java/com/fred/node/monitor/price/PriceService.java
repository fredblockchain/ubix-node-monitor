package com.fred.node.monitor.price;

import com.fred.node.monitor.price.coinmarketcap.CoinMarketCap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class PriceService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private CoinMarketCap cmc;

    private double lastPrice = 0.0;
    private String fiatIsoCode;

    public PriceService(@Value("${price.coinmarketcap.apikey}")String apiKey, @Value("${price.currency.code.iso}") String fiatIso) {
        this.cmc = new CoinMarketCap(apiKey);
        this.fiatIsoCode = fiatIso;
    }

    public TokenPrice getPrice(String token) {

        double pr = lastPrice;
        try {
            String json = this.cmc.callAPIforToken("UBX", this.fiatIsoCode);
            log.info(json);
            pr = this.cmc.extractPrice(json);


        } catch (IOException e) {
            e.printStackTrace();
        }
        TokenPrice tp = new TokenPrice(pr,this.fiatIsoCode);
        return tp;

    }

}
