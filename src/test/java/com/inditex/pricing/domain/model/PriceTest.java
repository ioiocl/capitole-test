package com.inditex.pricing.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

class PriceTest {

    @Test
    void shouldValidateDateRange() {
        Price price = Price.builder()
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                .endDate(LocalDateTime.parse("2020-06-14T23:59:59"))
                .build();

        assertThat(price.isValidForDate(LocalDateTime.parse("2020-06-14T15:00:00"))).isTrue();
        assertThat(price.isValidForDate(LocalDateTime.parse("2020-06-13T23:59:59"))).isFalse();
        assertThat(price.isValidForDate(LocalDateTime.parse("2020-06-15T00:00:00"))).isFalse();
    }

    @Test
    void shouldValidateProductId() {
        Price price = Price.builder()
                .productId(35455L)
                .build();

        assertThat(price.isValidForProduct(35455L)).isTrue();
        assertThat(price.isValidForProduct(35456L)).isFalse();
    }

    @Test
    void shouldValidateBrandId() {
        Price price = Price.builder()
                .brandId(1L)
                .build();

        assertThat(price.isValidForBrand(1L)).isTrue();
        assertThat(price.isValidForBrand(2L)).isFalse();
    }
}
