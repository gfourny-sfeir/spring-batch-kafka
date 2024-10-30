package com.example.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@ConfigurationProperties(prefix = "commande.batch")
@Validated
public record KafkaItemReaderProperties(
        @NotBlank String processName,
        @NotBlank String topic,
        @NotEmpty List<Integer> partitions,
        @Nonnull Integer pollTimeout,
        @Nullable List<PartitionOffset> partitionOffset
) {
    @Valid
    public record PartitionOffset(
            @Nonnull Integer partition,
            @Nonnull Long offset
    ){
    }
}
