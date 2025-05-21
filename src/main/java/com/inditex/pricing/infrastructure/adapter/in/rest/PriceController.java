package com.inditex.pricing.infrastructure.adapter.in.rest;

import com.inditex.pricing.domain.model.PriceNotFoundException;
import com.inditex.pricing.domain.port.in.GetPriceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;
    private final PriceResponseMapper priceResponseMapper;

    @GetMapping
    public ResponseEntity<PriceResponse> getPrice(
            @RequestParam Long brandId,
            @RequestParam Long productId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate) {
        
        return getPriceUseCase.getPrice(brandId, productId, applicationDate)
                .map(priceResponseMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> PriceNotFoundException.forProductAndBrand(productId, brandId));
    }
}
