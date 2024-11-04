package fr.example.config;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.model.Fourniture;

@Configuration
class PgWriterConfig {

    /**
     * Défini la requête d'insertion des données
     *
     * @return {@link Supplier}
     */
    @Bean
    Supplier<String> insertRequest() {
        return () -> /*language=PostgreSQL*/ """
                insert into fourniture (nom, prix_ht, fournisseur) values (?, ?, ?)
                """;
    }

    /**
     * Fourni une implementation d'{@link ItemPreparedStatementSetter} pour l'insertion des données
     *
     * @return {@link ItemPreparedStatementSetter}
     */
    @Bean
    ItemPreparedStatementSetter<Fourniture> insertPreparedStatement() {
        return (item, ps) -> {
            ps.setString(1, item.nom());
            ps.setBigDecimal(2, item.prixHT());
            ps.setString(3, item.fournisseur().nom());
        };
    }

    /**
     * {@link ItemWriter} intermédiaire permettant de construire un {@link Chunk} de {@link Fourniture}
     *
     * @param batchItemWriter {@link JdbcBatchItemWriter}
     * @return {@link ItemWriter}
     */
    @Bean
    public ItemWriter<List<Fourniture>> fournituresWriter(JdbcBatchItemWriter<Fourniture> batchItemWriter) {
        return items -> {
            Chunk<Fourniture> fournitures = new Chunk<>();
            for (var item : items) {
                for (var fourniture : item) {
                    fournitures.add(fourniture);
                }
            }
            batchItemWriter.write(fournitures);
        };
    }
}
