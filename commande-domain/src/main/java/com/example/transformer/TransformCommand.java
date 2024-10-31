package com.example.transformer;

import com.example.model.OutputFile;

import jakarta.annotation.Nonnull;

public interface TransformCommand {

    OutputFile transform(@Nonnull OutputFile outputFile);
}
