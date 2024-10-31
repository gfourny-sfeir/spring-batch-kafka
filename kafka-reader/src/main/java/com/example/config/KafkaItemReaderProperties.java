package com.example.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@ConfigurationProperties(prefix = "batch-kafka.reader")
@Validated
public record KafkaItemReaderProperties(
        /*
        Nom du processus
         */
        @NotBlank String processName,
        /*
        Nom du topic Kafka à consommer
         */
        @NotBlank String topic,
        /*
        Liste des partitions du topic à consommer
         */
        @NotEmpty List<Integer> partitions,
        /*
        Temps d'attente maximum après la consommation du dernier message avant de passer au step suivant
         */
        @Nonnull Integer pollTimeout,
        /*
        Spécification des offsets par partitions
         */
        @Nullable List<PartitionOffset> partitionOffset
) {
    @Valid
    public record PartitionOffset(
            /*
            Partition à configurer
             */
            @Nonnull Integer partition,
            /*
            Offset de début de consommation pour la partition
             */
            @Nonnull Long offset
    ) {
    }
}
