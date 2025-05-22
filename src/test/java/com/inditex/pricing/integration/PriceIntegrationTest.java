package com.inditex.pricing.integration;

import com.inditex.pricing.infrastructure.adapter.in.rest.PriceResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.ActiveProfiles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/prices-test.sql")
@ActiveProfiles("test")
class PriceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnPrice_Test1() {
        // Test 1: petición a las 10:00 del día 14
        String url = buildUrl(1L, 35455L, "2020-06-14T10:00:00");
        
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(url, PriceResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPriceList()).isEqualTo(1L);
        assertThat(response.getBody().getPrice()).isEqualByComparingTo(new BigDecimal("35.50"));
    }

    @Test
    void shouldReturnPrice_Test2() {
        // Test 2: petición a las 16:00 del día 14
        String url = buildUrl(1L, 35455L, "2020-06-14T16:00:00");
        
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(url, PriceResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPriceList()).isEqualTo(2L);
        assertThat(response.getBody().getPrice()).isEqualByComparingTo(new BigDecimal("25.45"));
    }

    @Test
    void shouldReturnPrice_Test3() {
        // Test 3: petición a las 21:00 del día 14
        String url = buildUrl(1L, 35455L, "2020-06-14T21:00:00");
        
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(url, PriceResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPriceList()).isEqualTo(1L);
        assertThat(response.getBody().getPrice()).isEqualByComparingTo(new BigDecimal("35.50"));
    }

    @Test
    void shouldReturnPrice_Test4() {
        // Test 4: petición a las 10:00 del día 15
        String url = buildUrl(1L, 35455L, "2020-06-15T10:00:00");
        
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(url, PriceResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPriceList()).isEqualTo(3L);
        assertThat(response.getBody().getPrice()).isEqualByComparingTo(new BigDecimal("30.50"));
    }

    @Test
    void shouldReturnPrice_Test5() {
        // Test 5: petición a las 21:00 del día 16
        String url = buildUrl(1L, 35455L, "2020-06-16T21:00:00");
        
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(url, PriceResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPriceList()).isEqualTo(4L);
        assertThat(response.getBody().getPrice()).isEqualByComparingTo(new BigDecimal("38.95"));
    }

    @Test
    void shouldReturnNotFound_WhenPriceDoesNotExist() {
        String url = buildUrl(99L, 35455L, "2020-06-14T10:00:00");
        
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(url, ErrorResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getError()).isEqualTo("NOT_FOUND");
    }

    @Test
    void shouldReturnBadRequest_WhenInvalidDateFormat() {
        String url = String.format("/api/prices?brandId=%d&productId=%d&applicationDate=%s",
                1L, 35455L, "invalid-date");
        
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(url, ErrorResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getError()).isEqualTo("BAD_REQUEST");
    }

    @Test
    void shouldReturnBadRequest_WhenNegativeBrandId() {
        String url = buildUrl(-1L, 35455L, "2020-06-14T10:00:00");
        
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(url, ErrorResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getError()).isEqualTo("VALIDATION_ERROR");
    }

    @Test
    void shouldReturnBadRequest_WhenZeroProductId() {
        String url = buildUrl(1L, 0L, "2020-06-14T10:00:00");
        
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(url, ErrorResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getError()).isEqualTo("VALIDATION_ERROR");
    }

    @Test
    void shouldReturnBadRequest_WhenMissingRequiredParameter() {
        String url = "/api/prices?brandId=1&productId=35455";
        
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(url, ErrorResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getError()).isEqualTo("BAD_REQUEST");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ErrorResponse {
        private String error;
        private String message;
    }

    @Test
    void shouldReturnPrice_ForDifferentProduct() {
        String url = buildUrl(1L, 35456L, "2020-06-14T16:00:00");
        
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(url, PriceResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPriceList()).isEqualTo(6L);
        assertThat(response.getBody().getPrice()).isEqualByComparingTo(new BigDecimal("35.45"));
    }

    @Test
    void shouldReturnPrice_ForDifferentBrand() {
        String url = buildUrl(2L, 35455L, "2020-06-14T10:00:00");
        
        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(url, PriceResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPriceList()).isEqualTo(7L);
        assertThat(response.getBody().getPrice()).isEqualByComparingTo(new BigDecimal("55.50"));
    }

    private String buildUrl(Long brandId, Long productId, String applicationDate) {
        return String.format("/api/prices?brandId=%d&productId=%d&applicationDate=%s",
                brandId, productId, applicationDate);
    }
}
