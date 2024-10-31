package com.example.transformer;

import com.example.model.OutputFile;

public interface TransformCommand {

    OutputFile transform(OutputFile outputFile);
}
