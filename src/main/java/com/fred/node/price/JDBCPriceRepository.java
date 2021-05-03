package com.fred.node.price;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Repository
public class JDBCPriceRepository implements PriceRepository {

    private final JdbcTemplate jdbcTemplate;

    public JDBCPriceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<TokenPrice> findPriceByTime(LocalDateTime t) {
        return Optional.empty();
    }

    @Override
    public void save(TokenPrice p) {
        jdbcTemplate.update(
                "INSERT INTO PRICES(token, time, price, currency) VALUES(?, ?, ?, ?)",
                p.getToken(), p.getPriceTime().toEpochSecond(ZoneOffset.UTC), p.getPrice(), p.getCurrencyIsoCode());
    }
}
