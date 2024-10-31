package fr.example;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.Write;
import com.example.model.OutputFile;
import com.example.saver.FileSaver;
import com.google.cloud.storage.BlobId;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class FileWriter implements Write<OutputFile> {

    private final FileSaver fileSaver;

    public BlobId write(@Nonnull OutputFile outputFile) {
        requireNonNull(outputFile, () -> "Le fichier à écrire ne doit pas être null");

        return fileSaver.save(
                () -> outputFile.toString().getBytes(StandardCharsets.UTF_8),
                UUID.randomUUID().toString(),
                builder -> builder.setMetadata(Map.of("custom-metadata", outputFile.fournisseur())
                ));
    }
}
