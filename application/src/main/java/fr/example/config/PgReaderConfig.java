package fr.example.config;

import java.util.Map;
import java.util.function.Consumer;

import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import com.example.model.OutputFile;

@Configuration
class PgReaderConfig {

    /**
     * Spécification de la requête permettant de récupérer les fournitures
     *
     * @return {@link Consumer}
     */
    @Bean
    Consumer<PostgresPagingQueryProvider> queryProviderConsumer() {
        return provider -> {
            provider.setSelectClause("*");
            provider.setFromClause("fourniture");
            provider.setWhereClause("treated = false");
            provider.setSortKeys(Map.of("id", Order.ASCENDING));
        };
    }

    /**
     * Spécification d'un {@link RowMapper} permettant de construire un {@link OutputFile} à partir des résultats de la requête
     *
     * @return {@link RowMapper}
     */
    @Bean
    RowMapper<OutputFile> rowMapper() {
        return (rs, rowNum) -> OutputFile.builder()
                .id(rs.getInt("id"))
                .nom(rs.getString("nom"))
                .prixHt(rs.getBigDecimal("prix_ht"))
                .fournisseur(rs.getString("fournisseur"))
                .build();

    }
}
