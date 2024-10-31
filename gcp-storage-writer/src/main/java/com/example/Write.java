package com.example;

import com.google.cloud.storage.BlobId;

public interface Write<T> {

    BlobId write(T t);
}
