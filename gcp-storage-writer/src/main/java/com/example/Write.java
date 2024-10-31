package com.example;

import com.google.cloud.storage.BlobId;

import jakarta.annotation.Nonnull;

public interface Write<T> {

    BlobId write(@Nonnull T t);
}
