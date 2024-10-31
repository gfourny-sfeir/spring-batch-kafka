package fr.example;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.model.OutputFile;
import com.example.saver.FileSaver;
import com.google.cloud.storage.BlobId;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FileWriter {

    private final FileSaver fileSaver;

    public BlobId write(OutputFile outputFile) {
        return fileSaver.save(
                () -> outputFile.toString().getBytes(StandardCharsets.UTF_8),
                UUID.randomUUID().toString(),
                builder -> builder.setMetadata(Map.of("custom-metadata", outputFile.fournisseur())
                ));
    }
}
