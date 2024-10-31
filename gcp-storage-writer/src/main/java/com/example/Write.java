package com.example;

import com.example.model.OutputFile;
import com.google.cloud.storage.BlobId;

public interface Write {

    BlobId write(OutputFile outputFile);
}
