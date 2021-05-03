package com.fred.node.price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {
    Optional<TokenPrice> findPriceByTime(LocalDateTime t);

    void save(TokenPrice p);
}
