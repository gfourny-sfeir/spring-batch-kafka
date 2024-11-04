package fr.example;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.example.model.Commande;
import com.example.model.Fournisseur;
import com.example.model.Fourniture;

import fr.example.config.ProducerConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBatchTest
@SpringJUnitConfig
@SpringBootTest
@Import(ProducerConfig.class)
@EnableBatchProcessing
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Lorsque de l'exécution du job")
@ExtendWith(OutputCaptureExtension.class)
public class ApplicationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private KafkaTemplate<String, Commande> kafkaTemplate;

    @Value("${batch-kafka.reader.topic}")
    private String topic;

    @BeforeEach
    void init() {
        final var commandes = buildCommandes();
        commandes.forEach(commande -> {
            var producerRecord = new ProducerRecord<>(topic, UUID.randomUUID().toString(), commande);
            kafkaTemplate.send(producerRecord);
        });
    }

    @DisplayName("Les messages doivent être consommés")
    @Test
    void jobExecution(CapturedOutput output) throws Exception {
        // Given ⌖

        // When 👉
        var jobExecution = jobLauncherTestUtils.launchJob();

        // Then ✅
        assertThat(jobExecution)
                .isNotNull()
                .extracting(JobExecution::getExitStatus)
                .isEqualTo(ExitStatus.COMPLETED);

        assertThat(output).contains("Mise à jour des éléments traités");
    }

    private List<Commande> buildCommandes() {
        return List.of(
                new Commande(List.of(
                        new Fourniture("pain", BigDecimal.valueOf(1.29), new Fournisseur("La mie parfaite", "adresse secrete", "18492354750365")),
                        new Fourniture("lait", BigDecimal.valueOf(1.44), new Fournisseur("Le lait", "adresse secrete du lait", "45218456395638"))
                )),
                new Commande(List.of(
                        new Fourniture("gateau", BigDecimal.valueOf(3.27), new Fournisseur("Miam", "LU", "75902548562749")),
                        new Fourniture("shampoing", BigDecimal.valueOf(4.90), new Fournisseur("Shampooo", "cedex", "64510367935471"))
                ))
        );
    }
}
