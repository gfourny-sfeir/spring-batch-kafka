package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

@ConfigurationProperties(prefix = "batch-postgres.reader")
@Validated
public record PostgresItemReaderProperties(
        /*
        Nom du processus
         */
        @NotBlank String processName,
        /*
        Nombre maximum d'éléments à récupérer
         */
        @Nonnull Integer fetchSize
) {
}
