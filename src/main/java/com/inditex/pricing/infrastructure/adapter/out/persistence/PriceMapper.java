package com.inditex.pricing.infrastructure.adapter.out.persistence;

import com.inditex.pricing.domain.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PriceMapper {
    Price toDomain(PriceEntity entity);
    PriceEntity toEntity(Price domain);
}
