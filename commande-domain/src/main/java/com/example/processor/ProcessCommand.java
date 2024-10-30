package com.example.processor;

import java.util.List;

import com.example.model.Commande;
import com.example.model.Fourniture;

public interface ProcessCommand {

    List<Fourniture> process(Commande commande);
}
