package com.example;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.simple.JdbcClient;

import com.example.config.PostgresItemWriterConfig;
import com.example.saver.Saver;

@AutoConfiguration
@ImportAutoConfiguration(PostgresItemWriterConfig.class)
public class PostgresWriterAutoConfiguration {

    @Bean
    Saver fournitureSaver(JdbcClient jdbcClient) {
        return new Saver(jdbcClient);
    }
}
