package com.example.writer;

import java.util.UUID;

import com.example.model.OutputFile;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class FileSaver {

    private final Storage storage;

    public BlobId save(OutputFile outputFile) {
        final var blobInfo = BlobInfo.newBuilder("bucket-fourniture", UUID.randomUUID().toString()).build();
        return storage.create(blobInfo, outputFile.toString().getBytes()).getBlobId();
    }
}
