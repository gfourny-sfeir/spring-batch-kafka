package com.example.writer;

import com.example.model.OutputFile;

public interface WriteFile<T, R> {

    R write(T t);
}
