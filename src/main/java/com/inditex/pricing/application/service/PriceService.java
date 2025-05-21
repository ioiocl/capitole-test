package com.inditex.pricing.application.service;

import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.port.in.GetPriceUseCase;
import com.inditex.pricing.domain.port.out.LoadPricePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PriceService implements GetPriceUseCase {
    
    private final LoadPricePort loadPricePort;

    @Override
    public Optional<Price> getPrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        if (brandId == null || brandId <= 0) {
            throw new IllegalArgumentException("Brand ID must be a positive number");
        }
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number");
        }
        if (applicationDate == null) {
            throw new IllegalArgumentException("Application date cannot be null");
        }

        return loadPricePort.findPrice(brandId, productId, applicationDate);
    }
}
