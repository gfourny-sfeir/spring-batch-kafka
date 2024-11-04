package fr.example.config;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.model.Commande;
import com.example.model.Fourniture;
import com.example.model.OutputFile;
import com.example.transformer.TransformCommand;

import fr.example.FiltrageCommandeProcessor;
import fr.example.StorageWriterListener;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
class BatchConfig {

    /**
     * Définition du job de traitement
     *
     * @param jobRepository   {@link JobRepository}
     * @param retrieveCommand {@link Step}
     * @param writeToBucket   {@link Step}
     * @return {@link Job}
     */
    @Bean
    Job commandJob(JobRepository jobRepository, Step retrieveCommand, Step writeToBucket) {
        return new JobBuilder("commandJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(retrieveCommand)
                .next(writeToBucket)
                .build();
    }

    /**
     * Etape de récupération des commandes :
     * <pre>
     *     - Consomme les partitions d'un topic Kafka configuré (KafkaItemReader)
     *     - Filtre les commandes (FilterCommand utilisé dans un ItemProcessor)
     *     - Enregistre les fournitures en base de données (ItemWriter)
     * </pre>
     *
     * @param jobRepository             {@link JobRepository}
     * @param transactionManager        {@link PlatformTransactionManager}
     * @param kafkaItemReader           {@link KafkaItemReader}
     * @param filtrageCommandeProcessor {@link ItemProcessor}
     * @param fournituresWriter         {@link ItemWriter}
     * @return {@link Step}
     */
    @Bean
    Step retrieveCommand(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            KafkaItemReader<String, Commande> kafkaItemReader,
            FiltrageCommandeProcessor filtrageCommandeProcessor,
            ItemWriter<List<Fourniture>> fournituresWriter) {

        return new StepBuilder("retrieveCommand", jobRepository)
                .<Commande, List<Fourniture>>chunk(10, transactionManager)
                .reader(kafkaItemReader)
                .processor(filtrageCommandeProcessor)
                .writer(fournituresWriter)
                .build();
    }

    /**
     * Etape d'enregistrement des fournitures sur le bucket GCP :
     * <pre>
     *     - Récupère les fournitures en base de données
     *     - Applique une transformation de fichier
     *     - Ecrit les fichiers sur le bucket
     * </pre>
     *
     * @param jobRepository         {@link JobRepository}
     * @param transactionManager    {@link PlatformTransactionManager}
     * @param fournitureItemReader  {@link JdbcPagingItemReader}
     * @param compositeItemWriter   {@link CompositeItemWriter}
     * @param transformCommand      {@link TransformCommand}
     * @param storageWriterListener {@link StorageWriterListener}
     * @return {@link Step}
     */
    @Bean
    Step writeToBucket(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JdbcPagingItemReader<OutputFile> fournitureItemReader,
            CompositeItemWriter<OutputFile> compositeItemWriter,
            TransformCommand transformCommand,
            StorageWriterListener storageWriterListener) {

        ItemProcessor<OutputFile, OutputFile> processor = transformCommand::transform;

        return new StepBuilder("writeToBucket", jobRepository)
                .<OutputFile, OutputFile>chunk(10, transactionManager)
                .reader(fournitureItemReader)
                .processor(processor)
                .writer(compositeItemWriter)
                .listener(storageWriterListener)
                .build();
    }
}
