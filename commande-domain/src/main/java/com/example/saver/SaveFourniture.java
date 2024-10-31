package com.example.saver;

import com.example.model.Fourniture;

import jakarta.annotation.Nonnull;

public interface SaveFourniture {

    void save(@Nonnull Fourniture fourniture);
}
