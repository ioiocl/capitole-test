package com.inditex.pricing.application.service;

import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.port.out.LoadPricePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    private LoadPricePort loadPricePort;

    private PriceService priceService;

    @BeforeEach
    void setUp() {
        priceService = new PriceService(loadPricePort);
    }

    @Test
    void shouldReturnPriceWhenFound() {
        // Given
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T10:00:00");
        
        Price expectedPrice = Price.builder()
                .brandId(brandId)
                .productId(productId)
                .price(new BigDecimal("35.50"))
                .build();

        when(loadPricePort.findPrice(brandId, productId, applicationDate))
                .thenReturn(Optional.of(expectedPrice));

        // When
        Optional<Price> result = priceService.getPrice(brandId, productId, applicationDate);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getPrice()).isEqualByComparingTo("35.50");
    }

    @Test
    void shouldReturnEmptyWhenNotFound() {
        // Given
        when(loadPricePort.findPrice(any(), any(), any()))
                .thenReturn(Optional.empty());

        // When
        Optional<Price> result = priceService.getPrice(1L, 35455L, LocalDateTime.now());

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenBrandIdIsNull() {
        assertThatThrownBy(() -> 
            priceService.getPrice(null, 35455L, LocalDateTime.now())
        ).isInstanceOf(IllegalArgumentException.class)
         .hasMessage("Brand ID must be a positive number");
    }

    @Test
    void shouldThrowExceptionWhenBrandIdIsNegative() {
        assertThatThrownBy(() -> 
            priceService.getPrice(-1L, 35455L, LocalDateTime.now())
        ).isInstanceOf(IllegalArgumentException.class)
         .hasMessage("Brand ID must be a positive number");
    }

    @Test
    void shouldThrowExceptionWhenProductIdIsNull() {
        assertThatThrownBy(() -> 
            priceService.getPrice(1L, null, LocalDateTime.now())
        ).isInstanceOf(IllegalArgumentException.class)
         .hasMessage("Product ID must be a positive number");
    }

    @Test
    void shouldThrowExceptionWhenDateIsNull() {
        assertThatThrownBy(() -> 
            priceService.getPrice(1L, 35455L, null)
        ).isInstanceOf(IllegalArgumentException.class)
         .hasMessage("Application date cannot be null");
    }
}
