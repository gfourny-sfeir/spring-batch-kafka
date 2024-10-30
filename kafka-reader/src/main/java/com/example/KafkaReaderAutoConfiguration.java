package com.example;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import com.example.config.KafkaItemReaderConfig;
import com.example.config.KafkaItemReaderProperties;

@AutoConfiguration
@EnableConfigurationProperties(KafkaItemReaderProperties.class)
@Import(KafkaItemReaderConfig.class)
public class KafkaReaderAutoConfiguration {
}
