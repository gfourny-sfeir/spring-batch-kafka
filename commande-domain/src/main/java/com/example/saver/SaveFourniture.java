package com.example.saver;

import java.util.List;

import com.example.model.Fourniture;

import jakarta.annotation.Nonnull;

public interface SaveFourniture {

    void save(@Nonnull List<Fourniture> fournitures);
}
