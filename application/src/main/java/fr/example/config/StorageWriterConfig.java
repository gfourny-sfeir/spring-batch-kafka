package fr.example.config;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.model.OutputFile;

@Configuration
class StorageWriterConfig {

    /**
     * Définition d'un {@link JdbcBatchItemWriter} permettant la mise à jours des fichiers traités en base de données
     *
     * @param dataSource {@link DataSource}
     * @return {@link JdbcBatchItemWriter}
     */
    @Bean
    JdbcBatchItemWriter<OutputFile> updateFournitureTreated(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<OutputFile>()
                .sql(/*language=PostgreSQL*/ """
                        update fourniture SET treated = true WHERE id = ?
                        """
                ).itemPreparedStatementSetter((item, ps) -> ps.setInt(1, item.id()))
                .dataSource(dataSource)
                .build();
    }

    /**
     * Définition d'un {@link CompositeItemWriter} permettant de chaîner deux {@link ItemWriter}:
     * <pre>
     *     - ItemWriter<OutputFile> fileItemWriter se charge de l'écriture sur le storage
     *     - JdbcBatchItemWriter<OutputFile> updateFournitureTreated se charge de mettre à jours les éléments écrit en base de données
     * </pre>
     *
     * @param fileItemWriter          {@link ItemWriter}
     * @param updateFournitureTreated {@link JdbcBatchItemWriter}
     * @return {@link CompositeItemWriter}
     */
    @Bean
    CompositeItemWriter<OutputFile> compositeItemWriter(
            @Qualifier("fileItemWriter") ItemWriter<OutputFile> fileItemWriter,
            JdbcBatchItemWriter<OutputFile> updateFournitureTreated) {
        var compositeItemWriter = new CompositeItemWriter<OutputFile>();
        compositeItemWriter.setDelegates(List.of(fileItemWriter, updateFournitureTreated));
        return compositeItemWriter;
    }
}
