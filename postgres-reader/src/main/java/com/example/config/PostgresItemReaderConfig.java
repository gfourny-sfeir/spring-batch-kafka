package com.example.config;

import java.util.function.Consumer;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import jakarta.annotation.Nonnull;

import static org.apache.commons.lang3.ObjectUtils.allNotNull;

@Configuration(proxyBeanMethods = false)
public class PostgresItemReaderConfig<T> {

    @Bean
    JdbcPagingItemReader<T> fournitureItemReader(
            @Nonnull DataSource dataSource,
            @Nonnull Consumer<PostgresPagingQueryProvider> queryProviderConsumer,
            @Nonnull PostgresItemReaderProperties properties,
            @Nonnull RowMapper<T> rowMapper) {

        allNotNull(dataSource, queryProviderConsumer, properties, rowMapper);

        PostgresPagingQueryProvider provider = new PostgresPagingQueryProvider();
        queryProviderConsumer.accept(provider);

        return new JdbcPagingItemReaderBuilder<T>()
                .name(properties.processName())
                .saveState(true)
                .dataSource(dataSource)
                .fetchSize(properties.fetchSize())
                .rowMapper(rowMapper)
                .queryProvider(provider)
                .build();
    }
}
