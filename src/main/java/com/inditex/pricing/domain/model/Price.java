package com.inditex.pricing.domain.model;

import lombok.Builder;
import lombok.Value;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class Price {
    Long id;
    Long brandId;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Long priceList;
    Long productId;
    Integer priority;
    BigDecimal price;
    String curr;

    public boolean isValidForDate(LocalDateTime applicationDate) {
        return !applicationDate.isBefore(startDate) && !applicationDate.isAfter(endDate);
    }

    public boolean isValidForProduct(Long productId) {
        return this.productId.equals(productId);
    }

    public boolean isValidForBrand(Long brandId) {
        return this.brandId.equals(brandId);
    }
}
