package com.example.filter;

import java.util.List;

import com.example.model.Commande;
import com.example.model.Fourniture;

public interface FilterCommand {

    List<Fourniture> filter(Commande commande);
}
