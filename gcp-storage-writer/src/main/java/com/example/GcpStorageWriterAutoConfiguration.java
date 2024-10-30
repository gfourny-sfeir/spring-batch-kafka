package com.example;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.example.config.GcpStorageItemWriterConfig;
import com.example.writer.FileSaver;
import com.google.cloud.storage.Storage;

@AutoConfiguration
@Import(GcpStorageItemWriterConfig.class)
public class GcpStorageWriterAutoConfiguration {

    @Bean
    FileSaver fileSaver(Storage storage) {
        return new FileSaver(storage);
    }
}
