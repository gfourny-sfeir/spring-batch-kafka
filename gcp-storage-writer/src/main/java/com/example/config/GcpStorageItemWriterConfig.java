package com.example.config;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import com.example.Write;

@EnableRetry
@Configuration(proxyBeanMethods = false)
public class GcpStorageItemWriterConfig {

    @Bean
    <T> ItemWriter<T> fileItemWriter(Write<T> writer) {
        return chunk -> {
            CopyOnWriteArrayList<CompletableFuture<Void>> futures = new CopyOnWriteArrayList<>();

            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                for (var item : chunk.getItems()) {
                    var future = CompletableFuture.runAsync(() -> writer.write(item), executor);
                    futures.add(future);
                }
            }
            futures.forEach(CompletableFuture::join);
        };
    }
}
