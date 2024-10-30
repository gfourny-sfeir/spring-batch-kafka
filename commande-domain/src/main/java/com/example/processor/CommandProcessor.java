package com.example.processor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.Commande;
import com.example.model.Fourniture;

public class CommandProcessor implements ProcessCommand {

    private static final Logger log = LoggerFactory.getLogger(CommandProcessor.class);

    @Override
    public List<Fourniture> process(Commande commande) {

        log.info("Processing command {}", commande);

        return commande.fournitures()
                .stream()
                .filter(fourniture -> fourniture.fournisseur().nom().equals("U-Tech"))
                .toList();
    }
}
