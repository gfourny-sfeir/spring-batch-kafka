package fr.example;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.config.PostgresItemWriterProperties;
import com.example.model.OutputFile;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class StorageWriterListener implements ItemWriteListener<OutputFile> {

    private final JdbcTemplate jdbcTemplate;
    private final PostgresItemWriterProperties properties;

    @Override
    public void afterWrite(@Nonnull Chunk<? extends OutputFile> items) {

        log.info("Mise à jour des éléments traités: {}", items.getItems());

        jdbcTemplate.batchUpdate(
                """
                        update fourniture SET treated = true WHERE id = ?
                        """,
                items.getItems(),
                properties.batchSize(),
                (ps, argument) -> ps.setInt(1, argument.id())
        );

        ItemWriteListener.super.afterWrite(items);
    }
}
