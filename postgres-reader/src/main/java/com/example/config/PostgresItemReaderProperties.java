package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

@ConfigurationProperties(prefix = "batch-postgres.reader")
@Validated
public record PostgresItemReaderProperties(
        @NotBlank String processName,
        @Nonnull Integer fetchSize
) {
}
