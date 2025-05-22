package com.inditex.pricing.infrastructure.adapter.in.rest;

import com.inditex.pricing.domain.model.PriceNotFoundException;
import com.inditex.pricing.domain.port.in.GetPriceUseCase;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
@Validated
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;
    private final PriceResponseMapperInterface mapper;

    @GetMapping
    public ResponseEntity<PriceResponse> getPrice(
            @RequestParam @NotNull(message = "Brand ID is required") 
            @Min(value = 1, message = "Brand ID must be greater than 0") Long brandId,
            
            @RequestParam @NotNull(message = "Product ID is required") 
            @Min(value = 1, message = "Product ID must be greater than 0") Long productId,
            
            @RequestParam @NotNull(message = "Application date is required") 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate) {
        
        return getPriceUseCase.getPrice(brandId, productId, applicationDate)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> PriceNotFoundException.forProductAndBrand(productId, brandId));
    }
}
