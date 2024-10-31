package com.example.saver;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import com.example.config.GcpStorageItemWriterProperties;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.apache.commons.lang3.ObjectUtils.allNotNull;

@RequiredArgsConstructor
@Slf4j
public class FileSaver {

    private final Storage storage;
    private final GcpStorageItemWriterProperties properties;

    @Retryable(
            maxAttemptsExpression = "${batch-storage.writer.retry-write.max-attempts}",
            backoff = @Backoff(
                    delayExpression = "${batch-storage.writer.retry-write.delay}",
                    maxDelayExpression = "${batch-storage.writer.retry-write.max-delay}"),
            retryFor = StorageException.class)
    public BlobId save(@Nonnull Supplier<byte[]> content, @Nonnull String fileName, @Nonnull Consumer<BlobInfo.Builder> blobInfoBuilder) {
        allNotNull(content, fileName, blobInfoBuilder);

        var builder = BlobInfo.newBuilder(properties.bucketName(), fileName);

        blobInfoBuilder.accept(builder);

        log.info("Ecriture du fichier {}", fileName);

        return storage.create(builder.build(), content.get()).getBlobId();
    }
}
