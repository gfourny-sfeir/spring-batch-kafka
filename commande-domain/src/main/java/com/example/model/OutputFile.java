package com.example.model;

import java.math.BigDecimal;

import jakarta.annotation.Nonnull;
import lombok.Builder;

@Builder
public record OutputFile(
        @Nonnull String nom,
        @Nonnull BigDecimal prixHt,
        @Nonnull String fournisseur
) {
}