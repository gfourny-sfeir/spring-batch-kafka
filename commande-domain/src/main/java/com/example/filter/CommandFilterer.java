package com.example.filter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.Commande;
import com.example.model.Fourniture;

import jakarta.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public class CommandFilterer implements FilterCommand {

    private static final Logger log = LoggerFactory.getLogger(CommandFilterer.class);

    @Override
    public List<Fourniture> filter(@Nonnull Commande commande) {
        requireNonNull(commande, () -> "La commande ne peut pas être null");

        log.info("Filtrage de la commande {}", commande);

        return commande.fournitures();
    }
}
