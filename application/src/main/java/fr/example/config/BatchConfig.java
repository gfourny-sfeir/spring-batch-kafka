package fr.example.config;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.filter.FilterCommand;
import com.example.model.Commande;
import com.example.model.Fourniture;
import com.example.model.OutputFile;
import com.example.transformer.TransformCommand;

import fr.example.StorageWriterListener;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class BatchConfig {

    @Bean
    Job commandJob(JobRepository jobRepository, Step retrieveCommand, Step writeToBucket) {
        return new JobBuilder("commandJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(retrieveCommand)
                .next(writeToBucket)
                .build();
    }

    @Bean
    Step retrieveCommand(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            KafkaItemReader<String, Commande> kafkaItemReader,
            FilterCommand filterCommand,
            ItemWriter<List<Fourniture>> postgresItemWriter) {

        ItemProcessor<Commande, List<Fourniture>> itemProcessor = filterCommand::filter;

        return new StepBuilder("retrieveCommand", jobRepository)
                .<Commande, List<Fourniture>>chunk(10, transactionManager)
                .reader(kafkaItemReader)
                .processor(itemProcessor)
                .writer(postgresItemWriter)
                .build();
    }

    @Bean
    Consumer<PostgresPagingQueryProvider> queryProviderConsumer() {
        return provider -> {
            provider.setSelectClause("*");
            provider.setFromClause("fourniture");
            provider.setWhereClause("treated = false");
            provider.setSortKeys(Map.of("id", Order.ASCENDING));
        };
    }

    @Bean
    RowMapper<?> rowMapper() {
        return (rs, rowNum) -> OutputFile.builder()
                .id(rs.getInt("id"))
                .nom(rs.getString("nom"))
                .prixHt(rs.getBigDecimal("prix_ht"))
                .fournisseur(rs.getString("fournisseur"))
                .build();

    }

    @Bean
    ItemProcessor<OutputFile, OutputFile> processor(TransformCommand transformCommand) {
        return transformCommand::transform;
    }

    @Bean
    Step writeToBucket(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JdbcPagingItemReader<OutputFile> fournitureItemReader,
            ItemWriter<OutputFile> fileItemWriter,
            ItemProcessor<OutputFile, OutputFile> processor,
            StorageWriterListener storageWriterListener) {
        return new StepBuilder("writeToBucket", jobRepository)
                .<OutputFile, OutputFile>chunk(10, transactionManager)
                .reader(fournitureItemReader)
                .processor(processor)
                .writer(fileItemWriter)
                .listener(storageWriterListener)
                .build();
    }
}
