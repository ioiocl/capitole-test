package com.inditex.pricing.domain.port.in;

import java.time.LocalDateTime;
import java.util.Optional;
import com.inditex.pricing.domain.model.Price;

public interface GetPriceUseCase {
    Optional<Price> getPrice(Long brandId, Long productId, LocalDateTime applicationDate);
}
