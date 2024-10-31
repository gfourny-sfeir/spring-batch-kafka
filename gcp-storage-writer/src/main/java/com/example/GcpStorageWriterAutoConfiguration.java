package com.example;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.example.config.GcpStorageItemWriterConfig;
import com.example.config.GcpStorageItemWriterProperties;
import com.example.writer.FileSaver;
import com.google.cloud.storage.Storage;

@AutoConfiguration
@EnableConfigurationProperties(GcpStorageItemWriterProperties.class)
@Import(GcpStorageItemWriterConfig.class)
public class GcpStorageWriterAutoConfiguration {

    @Bean
    FileSaver fileSaver(Storage storage, GcpStorageItemWriterProperties properties) {
        return new FileSaver(storage, properties);
    }
}
