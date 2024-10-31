package com.example.config;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import com.example.model.OutputFile;
import com.example.writer.FileSaver;

@EnableRetry
@Configuration(proxyBeanMethods = false)
public class GcpStorageItemWriterConfig {

    @Bean
    ItemWriter<OutputFile> fileItemWriter(FileSaver fileSaver) {
        return chunk -> {
            CopyOnWriteArrayList<CompletableFuture<Void>> futures = new CopyOnWriteArrayList<>();

            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                for (var outPutFile : chunk.getItems()) {
                    var future = CompletableFuture.runAsync(() -> fileSaver.save(
                            () -> outPutFile.toString().getBytes(StandardCharsets.UTF_8),
                            UUID.randomUUID().toString(),
                            builder -> builder.setMetadata(Map.of("custom-metadata", outPutFile.fournisseur()))), executor);
                    futures.add(future);

                }
            }
            futures.forEach(CompletableFuture::join);
        };
    }
}
