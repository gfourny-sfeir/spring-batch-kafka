package com.example.config;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.model.Fourniture;
import com.example.writer.FournitureSaver;

@Configuration(proxyBeanMethods = false)
public class ItemWriterConfig {

    @Bean
    ItemWriter<List<Fourniture>> fournitureItemWriter(FournitureSaver fournitureSaver) {

        return chunk -> {
            CopyOnWriteArrayList<CompletableFuture<Void>> futures = new CopyOnWriteArrayList<>();

            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                for (var items : chunk.getItems()) {
                    for (var fourniture : items) {
                        var future = CompletableFuture.runAsync(() -> fournitureSaver.save(fourniture), executor);
                        futures.add(future);
                    }

                }
            }
            futures.forEach(CompletableFuture::join);
        };
    }
}
