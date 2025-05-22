package com.inditex.pricing.infrastructure.adapter.in.rest;

import com.inditex.pricing.domain.model.Price;

public interface PriceResponseMapperInterface {
    PriceResponse toResponse(Price price);
}
