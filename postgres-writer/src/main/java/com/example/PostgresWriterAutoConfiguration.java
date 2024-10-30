package com.example;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.simple.JdbcClient;

import com.example.config.PostgresItemWriterConfig;
import com.example.writer.FournitureSaver;

@AutoConfiguration
@ImportAutoConfiguration(PostgresItemWriterConfig.class)
public class PostgresWriterAutoConfiguration {

    @Bean
    FournitureSaver fournitureSaver(JdbcClient jdbcClient) {
        return new FournitureSaver(jdbcClient);
    }
}
