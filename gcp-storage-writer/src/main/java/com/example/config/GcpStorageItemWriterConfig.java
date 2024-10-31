package com.example.config;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import com.example.writer.WriteFile;

@EnableRetry
@Configuration(proxyBeanMethods = false)
public class GcpStorageItemWriterConfig<T, R> {

    @Bean
    ItemWriter<T> fileItemWriter(WriteFile<T, R> writeFile) {
        return chunk -> {
            CopyOnWriteArrayList<CompletableFuture<Void>> futures = new CopyOnWriteArrayList<>();

            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                for (var t : chunk.getItems()) {
                    var future = CompletableFuture.runAsync(() -> writeFile.write(t), executor);
                    futures.add(future);
                }
            }
            futures.forEach(CompletableFuture::join);
        };
    }
}
