package com.example.config;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.model.OutputFile;
import com.example.writer.FileSaver;

@Configuration(proxyBeanMethods = false)
public class GcpStorageItemWriterConfig {

    @Bean
    ItemWriter<OutputFile> fileItemWriter(FileSaver fileSaver) {
        return chunk -> {
            CopyOnWriteArrayList<CompletableFuture<Void>> futures = new CopyOnWriteArrayList<>();

            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                for (var outPutFile : chunk.getItems()) {
                    var future = CompletableFuture.runAsync(() -> fileSaver.save(outPutFile), executor);
                    futures.add(future);

                }
            }
            futures.forEach(CompletableFuture::join);
        };
    }
}
