package com.inditex.pricing.domain.model;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(String message) {
        super(message);
    }

    public static PriceNotFoundException forProductAndBrand(Long productId, Long brandId) {
        return new PriceNotFoundException(
            String.format("No price found for product ID %d and brand ID %d", productId, brandId)
        );
    }
}
