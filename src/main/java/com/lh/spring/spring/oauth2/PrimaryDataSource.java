package com.lh.spring.spring.oauth2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class PrimaryDataSource {
    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primarydb() {
        return DataSourceBuilder.create().build();
    }
}
