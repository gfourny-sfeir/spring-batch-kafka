package com.example.saver;

import java.util.Map;

import org.springframework.jdbc.core.simple.JdbcClient;

import com.example.model.Fourniture;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.apache.commons.lang3.ObjectUtils.allNotNull;

@RequiredArgsConstructor
@Slf4j
public class Saver {

    private final JdbcClient jdbcClient;

    public void save(@Nonnull Fourniture fourniture, @Nonnull String request, @Nonnull Map<String, ?> parameters) {
        allNotNull(fourniture, request, parameters);

        log.info("Saving fourniture {}", fourniture);

        jdbcClient.sql(request)
                .params(parameters)
                .update();
    }
}
