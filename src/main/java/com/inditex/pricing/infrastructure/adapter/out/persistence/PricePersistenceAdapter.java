package com.inditex.pricing.infrastructure.adapter.out.persistence;

import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.port.out.LoadPricePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PricePersistenceAdapter implements LoadPricePort {

    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    @Override
    public Optional<Price> findPrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        return priceRepository
                .findTopByBrandIdAndProductIdAndDateOrderByPriorityDesc(brandId, productId, applicationDate)
                .map(priceMapper::toDomain);
    }
}
