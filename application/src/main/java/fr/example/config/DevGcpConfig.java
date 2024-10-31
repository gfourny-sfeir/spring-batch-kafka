package fr.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.cloud.NoCredentials;
import com.google.cloud.spring.core.GcpProjectIdProvider;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Configuration
public class DevGcpConfig {

    @Bean
    Storage storage(GcpProjectIdProvider projectIdProvider) {
        return StorageOptions.newBuilder()
                .setCredentials(NoCredentials.getInstance())
                .setProjectId(projectIdProvider.getProjectId())
                .setHost("http://storage:8000")
                .build()
                .getService();
    }
}
