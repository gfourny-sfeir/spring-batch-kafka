package com.example.writer;

import java.util.Map;

import org.springframework.jdbc.core.simple.JdbcClient;

import com.example.model.Fourniture;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class FournitureSaver {

    private final JdbcClient jdbcClient;

    public void save(Fourniture fourniture) {
        log.info("Saving fourniture {}", fourniture);
        jdbcClient.sql("""
                        insert into fourniture (nom, prix_ht, fournisseur) values (:nom, :prixHT, :nomFournisseur)
                        """)
                .params(Map.of(
                        "nom", fourniture.nom(),
                        "prixHT", fourniture.prixHT(),
                        "nomFournisseur", fourniture.fournisseur().nom()
                ))
                .update();
    }
}
