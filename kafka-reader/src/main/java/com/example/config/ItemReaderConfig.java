package com.example.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.model.Commande;

@Configuration(proxyBeanMethods = false)
public class ItemReaderConfig {

    @Bean
    KafkaItemReader<String, Commande> itemReader(KafkaProperties kafkaProperties) {
        var builder = new KafkaItemReaderBuilder<String, Commande>();
        var properties = new Properties();
        properties.putAll(kafkaProperties.buildConsumerProperties(null));
        builder.consumerProperties(properties);

        return builder.partitions(0, 1, 2)
                .topic("appro-event")
                .saveState(true)
                .name("retrieveCommandes")
                .partitionOffsets(new HashMap<>())
                .pollTimeout(Duration.ofSeconds(5))
                .build();
    }
}
