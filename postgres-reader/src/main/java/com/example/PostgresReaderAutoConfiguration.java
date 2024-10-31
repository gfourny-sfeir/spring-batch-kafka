package com.example;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import com.example.config.PostgresItemReaderConfig;
import com.example.config.PostgresItemReaderProperties;

@AutoConfiguration
@Import(PostgresItemReaderConfig.class)
@EnableConfigurationProperties(PostgresItemReaderProperties.class)
public class PostgresReaderAutoConfiguration {
}
