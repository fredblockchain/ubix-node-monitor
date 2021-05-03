package com.fred.node.price;

import com.fred.node.price.coinmarketcap.CoinMarketCap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class PriceService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JDBCPriceRepository dbRepo;

    private CoinMarketCap cmc;

    private double lastPrice = 0.0;
    private LocalDateTime priceTime;
    private String fiatIsoCode;

    public PriceService(@Value("${price.coinmarketcap.apikey}")String apiKey, @Value("${price.currency.code.iso}") String fiatIso) {
        this.cmc = new CoinMarketCap(apiKey);
        this.fiatIsoCode = fiatIso;
    }

    @Scheduled(fixedDelay = 1800000) // in milliseconds
    public void checkPrices (){
        double priceCMC = 0;
        try {
            String json = this.cmc.callAPIforToken("UBX", fiatIsoCode);
            log.info(json);
            priceCMC = this.cmc.extractPrice(json);

        } catch (IOException e) {
            log.error("error retrieving price from CMC",e);
        }
        LocalDateTime priceTime = LocalDateTime.ofEpochSecond(System.currentTimeMillis(),0,ZoneOffset.UTC);
        TokenPrice tp = new TokenPrice("UBX", priceCMC, fiatIsoCode, priceTime);
        if(priceCMC != 0) {
            this.priceTime = priceTime;
            this.lastPrice = priceCMC;
            this.dbRepo.save(tp);
        }
    }

    public TokenPrice getPrice(String token) {
        return new TokenPrice(token, lastPrice, fiatIsoCode, priceTime);

    }
}
