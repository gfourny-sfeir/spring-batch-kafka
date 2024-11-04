package com.example;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.config.PostgresItemWriterConfig;
import com.example.config.PostgresItemWriterProperties;
import com.example.saver.Saver;

@AutoConfiguration
@ImportAutoConfiguration(PostgresItemWriterConfig.class)
public class PostgresWriterAutoConfiguration {

    @Bean
    Saver saver(JdbcTemplate jdbcTemplate, PostgresItemWriterProperties properties) {
        return new Saver(jdbcTemplate, properties);
    }
}
