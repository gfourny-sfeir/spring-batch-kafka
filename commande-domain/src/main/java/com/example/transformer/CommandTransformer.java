package com.example.transformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.OutputFile;

public class CommandTransformer implements TransformCommand {

    private static final Logger log = LoggerFactory.getLogger(CommandTransformer.class);

    @Override
    public OutputFile transform(OutputFile outputFile) {
        log.info("Processing file {}", outputFile);
        return outputFile;
    }
}
