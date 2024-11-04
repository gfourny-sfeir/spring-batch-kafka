package com.example.config;

import java.util.function.Supplier;

import javax.sql.DataSource;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class PostgresItemWriterConfig {

    @Bean
    <T> JdbcBatchItemWriter<T> batchItemWriter(
            DataSource dataSource,
            Supplier<String> insertRequest,
            ItemPreparedStatementSetter<T> insertPreparedStatement) {

        return new JdbcBatchItemWriterBuilder<T>().sql(insertRequest.get())
                .itemPreparedStatementSetter(insertPreparedStatement)
                .dataSource(dataSource)
                .assertUpdates(true)
                .build();
    }
}
