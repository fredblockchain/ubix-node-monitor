package com.fred.node.price.coinmarketcap;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoinMarketCapTest {

    @Test
    void extractPriceUSD() throws JsonProcessingException {
        CoinMarketCap cmc = new CoinMarketCap("noKeyNeededForThisTest");
        double price = cmc.extractPrice(this.jsonExampleOK_usd_bigPrice);
        assertEquals(123456.00123953241454, price);
    }

    @Test
    void extractPriceEUR() throws JsonProcessingException {
        CoinMarketCap cmc = new CoinMarketCap("noKeyNeededForThisTest");
        double price = cmc.extractPrice(this.jsonExampleOK_eur_smallPrice);
        assertEquals(0.0000123953241454, price);
    }


    private String jsonExampleOK_usd_bigPrice = "{\"status\":{\"timestamp\":\"2021-04-23T18:05:08.295Z\",\"error_code\":0,\"error_message\":null,\"elapsed\":21,\"credit_count\":1,\"notice\":null},\"data\":{\"UBX\":{\"id\":7622,\"name\":\"UBIX.Network\",\"symbol\":\"UBX\",\"slug\":\"ubix-network\",\"num_market_pairs\":4,\"date_added\":\"2020-11-09T00:00:00.000Z\",\"tags\":[\"ethereum\"],\"max_supply\":1000000000000,\"circulating_supply\":0,\"total_supply\":1000000000000,\"is_active\":1,\"platform\":null,\"cmc_rank\":2527,\"is_fiat\":0,\"last_updated\":\"2021-04-23T18:04:06.000Z\",\"quote\":{\"USD\":{\"price\":123456.00123953241454,\"volume_24h\":4729015.29992298,\"percent_change_1h\":-1.60385745,\"percent_change_24h\":-26.04304919,\"percent_change_7d\":-62.68000886,\"percent_change_30d\":419.34919415,\"percent_change_60d\":4422.29181957,\"percent_change_90d\":14114.82919017,\"market_cap\":0,\"last_updated\":\"2021-04-23T18:04:06.000Z\"}}}}}\n";

    private String jsonExampleOK_eur_smallPrice = "{\"status\":{\"timestamp\":\"2021-04-23T18:05:08.295Z\",\"error_code\":0,\"error_message\":null,\"elapsed\":21,\"credit_count\":1,\"notice\":null},\"data\":{\"UBX\":{\"id\":7622,\"name\":\"UBIX.Network\",\"symbol\":\"UBX\",\"slug\":\"ubix-network\",\"num_market_pairs\":4,\"date_added\":\"2020-11-09T00:00:00.000Z\",\"tags\":[\"ethereum\"],\"max_supply\":1000000000000,\"circulating_supply\":0,\"total_supply\":1000000000000,\"is_active\":1,\"platform\":null,\"cmc_rank\":2527,\"is_fiat\":0,\"last_updated\":\"2021-04-23T18:04:06.000Z\",\"quote\":{\"EUR\":{\"price\":0.0000123953241454,\"volume_24h\":4729015.29992298,\"percent_change_1h\":-1.60385745,\"percent_change_24h\":-26.04304919,\"percent_change_7d\":-62.68000886,\"percent_change_30d\":419.34919415,\"percent_change_60d\":4422.29181957,\"percent_change_90d\":14114.82919017,\"market_cap\":0,\"last_updated\":\"2021-04-23T18:04:06.000Z\"}}}}}\n";

}