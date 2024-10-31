package com.example.config;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.kafka.common.TopicPartition;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class KafkaItemReaderConfig<T, R> {

    @Bean
    KafkaItemReader<T, R> itemReader(KafkaProperties kafkaProperties, KafkaItemReaderProperties kafkaItemReaderProperties) {
        var properties = new Properties();
        properties.putAll(kafkaProperties.buildConsumerProperties(null));

        final var partitionOffsets = buildTopicPartition(kafkaItemReaderProperties);

        return new KafkaItemReaderBuilder<T, R>()
                .consumerProperties(properties)
                .partitions(kafkaItemReaderProperties.partitions())
                .topic(kafkaItemReaderProperties.topic())
                .saveState(true)
                .name(kafkaItemReaderProperties.processName())
                .partitionOffsets(partitionOffsets)
                .pollTimeout(Duration.ofSeconds(kafkaItemReaderProperties.pollTimeout()))
                .build();
    }

    private HashMap<TopicPartition, Long> buildTopicPartition(KafkaItemReaderProperties kafkaItemReaderProperties) {

        record PartitionOffset(TopicPartition topicPartition, long offset) {
        }

        return Optional.ofNullable(kafkaItemReaderProperties.partitionOffset())
                .stream()
                .flatMap(Collection::stream)
                .map(partitionOffset -> new PartitionOffset(
                        new TopicPartition(kafkaItemReaderProperties.topic(), partitionOffset.partition()),
                        partitionOffset.offset()
                ))
                .collect(Collectors.toMap(
                        PartitionOffset::topicPartition,
                        PartitionOffset::offset,
                        (first, second) -> second,
                        HashMap::new)
                );
    }
}
