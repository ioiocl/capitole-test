package com.inditex.pricing.infrastructure.adapter.in.rest;

import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.model.PriceNotFoundException;
import com.inditex.pricing.domain.port.in.GetPriceUseCase;
import com.inditex.pricing.infrastructure.config.MapperConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
@Import({MapperConfig.class, PriceResponseMapper.class})
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetPriceUseCase getPriceUseCase;

    @MockBean
    private PriceResponseMapper priceResponseMapper;

    @Test
    void shouldReturnPrice() throws Exception {
        // Given
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T10:00:00");
        Price price = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(1L)
                .startDate(applicationDate.minusHours(10))
                .endDate(applicationDate.plusHours(10))
                .price(new BigDecimal("35.50"))
                .build();

        PriceResponse priceResponse = PriceResponse.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(1L)
                .startDate(applicationDate.minusHours(10))
                .endDate(applicationDate.plusHours(10))
                .price(new BigDecimal("35.50"))
                .build();

        when(getPriceUseCase.getPrice(1L, 35455L, applicationDate))
                .thenReturn(Optional.of(price));
        when(priceResponseMapper.toResponse(price))
                .thenReturn(priceResponse);

        // When/Then
        mockMvc.perform(get("/api/prices")
                .param("brandId", "1")
                .param("productId", "35455")
                .param("applicationDate", "2020-06-14T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    void shouldReturn404WhenPriceNotFound() throws Exception {
        // Given
        when(getPriceUseCase.getPrice(any(), any(), any()))
                .thenReturn(Optional.empty());

        // When/Then
        mockMvc.perform(get("/api/prices")
                .param("brandId", "1")
                .param("productId", "35455")
                .param("applicationDate", "2020-06-14T10:00:00"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400WhenInvalidDate() throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("brandId", "1")
                .param("productId", "35455")
                .param("applicationDate", "invalid-date"))
                .andExpect(status().isBadRequest());
    }
}
