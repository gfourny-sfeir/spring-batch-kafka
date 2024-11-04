package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "batch-postgres.writer")
@Validated
public record PostgresItemWriterProperties(
        @NotNull Integer batchSize
) {
}
