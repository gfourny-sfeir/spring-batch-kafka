package com.example.saver;

import java.util.Map;

import org.springframework.jdbc.core.simple.JdbcClient;

import com.example.model.Fourniture;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class Saver {

    private final JdbcClient jdbcClient;

    public void save(Fourniture fourniture, String request, Map<String, ?> parameters) {
        log.info("Saving fourniture {}", fourniture);

        jdbcClient.sql(request)
                .params(parameters)
                .update();
    }
}
