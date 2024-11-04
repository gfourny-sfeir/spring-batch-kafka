package com.example;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import com.example.config.PostgresItemWriterConfig;

@AutoConfiguration
@ImportAutoConfiguration(PostgresItemWriterConfig.class)
public class PostgresWriterAutoConfiguration {

}
