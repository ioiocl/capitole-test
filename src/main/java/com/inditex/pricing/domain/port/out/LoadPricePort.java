package com.inditex.pricing.domain.port.out;

import java.time.LocalDateTime;
import java.util.Optional;
import com.inditex.pricing.domain.model.Price;

public interface LoadPricePort {
    Optional<Price> findPrice(Long brandId, Long productId, LocalDateTime applicationDate);
}
