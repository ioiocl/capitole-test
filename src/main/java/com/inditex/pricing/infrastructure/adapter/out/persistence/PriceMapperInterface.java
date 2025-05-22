package com.inditex.pricing.infrastructure.adapter.out.persistence;

import com.inditex.pricing.domain.model.Price;

public interface PriceMapperInterface {
    Price toDomain(PriceEntity priceEntity);
}
