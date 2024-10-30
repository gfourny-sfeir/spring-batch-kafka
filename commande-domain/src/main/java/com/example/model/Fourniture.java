package com.example.model;

import java.math.BigDecimal;

import jakarta.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public record Fourniture(
        @Nonnull String nom,
        @Nonnull BigDecimal prixHT,
        @Nonnull Fournisseur fournisseur
) {
    public Fourniture {
        requireNonNull(nom, () -> "Le nom de la fourniture est obligatoire");
        requireNonNull(prixHT, () -> "Le prix hors taxe est obligatoire");
        requireNonNull(fournisseur, () -> "Le fournisseur est obligatoire");
    }
}
