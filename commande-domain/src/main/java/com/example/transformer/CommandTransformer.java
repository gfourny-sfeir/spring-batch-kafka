package com.example.transformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.OutputFile;

import jakarta.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public class CommandTransformer implements TransformCommand {

    private static final Logger log = LoggerFactory.getLogger(CommandTransformer.class);

    @Override
    public OutputFile transform(@Nonnull OutputFile outputFile) {
        requireNonNull(outputFile, () -> "L'OutputFile ne peut pas être null");

        log.info("Traitement du fichier {}", outputFile);
        return outputFile;
    }
}
