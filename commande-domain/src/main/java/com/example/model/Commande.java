package com.example.model;

import java.util.List;

import jakarta.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public record Commande(
        @Nonnull List<Fourniture> fournitures
) {

    public Commande(@Nonnull List<Fourniture> fournitures) {
        requireNonNull(fournitures, () -> "La liste des fournitures est obligatoire");
        this.fournitures = List.copyOf(fournitures);
    }
}
