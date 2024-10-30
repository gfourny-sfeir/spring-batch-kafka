package com.example;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

import com.example.config.ItemReaderConfig;

@AutoConfiguration
@Import(ItemReaderConfig.class)
public class KafkaReaderAutoConfiguration {
}
