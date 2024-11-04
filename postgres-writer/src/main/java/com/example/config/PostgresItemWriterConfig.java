package com.example.config;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

import org.springframework.batch.item.ItemWriter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.model.Fourniture;
import com.example.saver.SaveFourniture;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(PostgresItemWriterProperties.class)
public class PostgresItemWriterConfig {

    @Bean
    ItemWriter<List<Fourniture>> postgresItemWriter(SaveFourniture saver) {
        return chunk -> {
            CopyOnWriteArrayList<CompletableFuture<Void>> futures = new CopyOnWriteArrayList<>();

            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                for (var items : chunk.getItems()) {
                    var future = CompletableFuture.runAsync(() -> saver.save(items), executor);
                    futures.add(future);
                }
            }
            futures.forEach(CompletableFuture::join);
        };
    }
}
