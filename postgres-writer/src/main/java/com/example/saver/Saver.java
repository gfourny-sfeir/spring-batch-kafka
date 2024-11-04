package com.example.saver;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import com.example.config.PostgresItemWriterProperties;
import com.example.model.Fourniture;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.apache.commons.lang3.ObjectUtils.allNotNull;

@RequiredArgsConstructor
@Slf4j
public class Saver {

    private final JdbcTemplate jdbcTemplate;
    private final PostgresItemWriterProperties properties;

    public void save(@Nonnull List<Fourniture> fournitures, @Nonnull String request, @Nonnull ParameterizedPreparedStatementSetter<Fourniture> pss) {
        allNotNull(fournitures, request, pss);

        log.info("Enregistrement des fournitures {}", fournitures);

        jdbcTemplate.batchUpdate(
                request,
                fournitures,
                properties.batchSize(),
                pss
        );
    }
}
