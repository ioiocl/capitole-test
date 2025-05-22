package com.inditex.pricing.infrastructure.adapter.out.persistence;

import com.inditex.pricing.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({PricePersistenceAdapter.class, TestJpaConfig.class})
@Sql("/prices-test.sql")
class PricePersistenceAdapterTest {

    @Autowired
    private PricePersistenceAdapter adapter;

    @Test
    void shouldFindPriceForTest1() {
        // Test 1: petición a las 10:00 del día 14
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T10:00:00");
        Optional<Price> price = adapter.findPrice(1L, 35455L, applicationDate);

        assertThat(price).isPresent();
        assertThat(price.get().getPriceList()).isEqualTo(1L);
    }

    @Test
    void shouldFindPriceForTest2() {
        // Test 2: petición a las 16:00 del día 14
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T16:00:00");
        Optional<Price> price = adapter.findPrice(1L, 35455L, applicationDate);

        assertThat(price).isPresent();
        assertThat(price.get().getPriceList()).isEqualTo(2L);
    }

    @Test
    void shouldFindPriceForTest3() {
        // Test 3: petición a las 21:00 del día 14
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T21:00:00");
        Optional<Price> price = adapter.findPrice(1L, 35455L, applicationDate);

        assertThat(price).isPresent();
        assertThat(price.get().getPriceList()).isEqualTo(1L);
    }

    @Test
    void shouldFindPriceForTest4() {
        // Test 4: petición a las 10:00 del día 15
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-15T10:00:00");
        Optional<Price> price = adapter.findPrice(1L, 35455L, applicationDate);

        assertThat(price).isPresent();
        assertThat(price.get().getPriceList()).isEqualTo(3L);
    }

    @Test
    void shouldFindPriceForTest5() {
        // Test 5: petición a las 21:00 del día 16
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-16T21:00:00");
        Optional<Price> price = adapter.findPrice(1L, 35455L, applicationDate);

        assertThat(price).isPresent();
        assertThat(price.get().getPriceList()).isEqualTo(4L);
    }

    @Test
    void shouldFindPriceForDifferentProduct() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T16:00:00");
        Long brandId = 1L;
        Long productId = 35456L;

        // When
        Optional<Price> price = adapter.findPrice(brandId, productId, applicationDate);

        // Then
        assertThat(price).isPresent();
        assertThat(price.get().getPrice()).isEqualByComparingTo(new BigDecimal("35.45"));
        assertThat(price.get().getPriceList()).isEqualTo(6L);
    }

    @Test
    void shouldFindPriceForDifferentBrand() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T10:00:00");
        Long brandId = 2L;
        Long productId = 35455L;

        // When
        Optional<Price> price = adapter.findPrice(brandId, productId, applicationDate);

        // Then
        assertThat(price).isPresent();
        assertThat(price.get().getPrice()).isEqualByComparingTo(new BigDecimal("55.50"));
        assertThat(price.get().getPriceList()).isEqualTo(7L);
    }

    @Test
    void shouldNotFindPriceForNonExistentProduct() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T10:00:00");
        Long brandId = 1L;
        Long productId = 99999L;

        // When
        Optional<Price> price = adapter.findPrice(brandId, productId, applicationDate);

        // Then
        assertThat(price).isEmpty();
    }

    @Test
    void shouldNotFindPriceForNonExistentBrand() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T10:00:00");
        Long brandId = 99L;
        Long productId = 35455L;

        // When
        Optional<Price> price = adapter.findPrice(brandId, productId, applicationDate);

        // Then
        assertThat(price).isEmpty();
    }
}
