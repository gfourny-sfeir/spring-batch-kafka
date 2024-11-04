package fr.example;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

import com.example.model.OutputFile;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class StorageWriterListener implements ItemWriteListener<OutputFile> {

    @Override
    public void afterWrite(@Nonnull Chunk<? extends OutputFile> items) {

        log.info("Mise à jour des éléments traités: {}", items.getItems());

        ItemWriteListener.super.afterWrite(items);
    }
}
