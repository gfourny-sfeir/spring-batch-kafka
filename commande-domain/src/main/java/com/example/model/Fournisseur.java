package com.example.model;

import jakarta.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public record Fournisseur(
        @Nonnull String nom,
        @Nonnull String adresse,
        @Nonnull String siret
) {

    public Fournisseur {
        requireNonNull(nom, () -> "Le nom du fournisseur est obligatoire.");
        requireNonNull(adresse, () -> "L'adresse de fournisseur est obligatoire.");
        requireNonNull(siret, () -> "Le numéro de siret du fournisseur est obligatoire.");
    }
}
