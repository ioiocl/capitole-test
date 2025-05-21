package com.inditex.pricing.infrastructure.adapter.in.rest;

import com.inditex.pricing.domain.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PriceResponseMapper {
    PriceResponse toResponse(Price price);
}
