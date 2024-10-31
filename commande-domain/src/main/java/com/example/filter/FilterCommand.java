package com.example.filter;

import java.util.List;

import com.example.model.Commande;
import com.example.model.Fourniture;

import jakarta.annotation.Nonnull;

public interface FilterCommand {

    List<Fourniture> filter(@Nonnull Commande commande);
}
