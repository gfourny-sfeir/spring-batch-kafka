package com.example.filter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.Commande;
import com.example.model.Fourniture;

public class CommandFilterer implements FilterCommand {

    private static final Logger log = LoggerFactory.getLogger(CommandFilterer.class);

    @Override
    public List<Fourniture> filter(Commande commande) {

        log.info("Filtering command {}", commande);

        return commande.fournitures();
    }
}
