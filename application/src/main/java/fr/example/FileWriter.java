package fr.example;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

import com.example.model.OutputFile;
import com.example.saver.FileSaver;
import com.example.writer.WriteFile;
import com.google.cloud.storage.BlobId;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileWriter implements WriteFile<OutputFile, BlobId> {

    private final FileSaver fileSaver;

    @Override
    public BlobId write(OutputFile outputFile) {
        return fileSaver.save(
                () -> outputFile.toString().getBytes(StandardCharsets.UTF_8),
                UUID.randomUUID().toString(),
                builder -> builder.setMetadata(Map.of("custom-metadata", outputFile.fournisseur())
                ));
    }
}
