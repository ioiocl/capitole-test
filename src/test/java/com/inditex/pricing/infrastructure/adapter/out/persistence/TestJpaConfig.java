package com.inditex.pricing.infrastructure.adapter.out.persistence;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@TestConfiguration
@EnableJpaRepositories(basePackages = "com.inditex.pricing.infrastructure.adapter.out.persistence")
public class TestJpaConfig {

    @Bean
    public PriceMapper priceMapper() {
        return new PriceMapperImpl();
    }
}
