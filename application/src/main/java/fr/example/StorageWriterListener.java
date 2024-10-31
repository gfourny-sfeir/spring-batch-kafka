package fr.example;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import com.example.model.OutputFile;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class StorageWriterListener implements ItemWriteListener<OutputFile> {

    private final JdbcClient jdbcClient;

    @Override
    public void afterWrite(@Nonnull Chunk<? extends OutputFile> items) {

        var completableFutures = new ArrayList<CompletableFuture<Void>>();

        for (OutputFile outputFile : items.getItems()) {
            log.info("Mise à jour de l'élément traité: {}", outputFile);
            try (var executor = Executors.newSingleThreadExecutor()) {
                var future = CompletableFuture.runAsync(() ->
                        jdbcClient.sql("""
                                        update fourniture SET treated = true WHERE id = :id
                                        """)
                                .param("id", outputFile.id())
                                .update(), executor);

                completableFutures.add(future);
            }
        }
        completableFutures.forEach(CompletableFuture::join);
        ItemWriteListener.super.afterWrite(items);
    }
}
