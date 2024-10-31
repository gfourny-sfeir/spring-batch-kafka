package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Validated
@ConfigurationProperties(prefix = "batch-storage.writer")
public record GcpStorageItemWriterProperties(
        /*
         Nom du bucket
         */
        @NotBlank String bucketName,
        /*
        Configuration de la politique de retry sur l'écriture du fichier
         */
        @Nonnull RetryWrite retryWrite
) {

    @Valid
    public record RetryWrite(
            @Nonnull Integer maxAttempts,
            @Nonnull Long delay,
            @Nonnull Long maxDelay
    ) {
    }
}
