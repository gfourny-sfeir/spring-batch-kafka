package fr.example.config;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.model.Commande;
import com.example.model.Fourniture;
import com.example.processor.ProcessCommand;

@Configuration
public class BatchConfig {

    @Bean
    Job filtrageJob(JobRepository jobRepository, Step retrieveCommand) {
        return new JobBuilder("filtrageJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(retrieveCommand)
                .build();
    }

    @Bean
    Step retrieveCommand(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            ItemReader<Commande> kafkaItemReader,
            ProcessCommand processCommand,
            ItemWriter<List<Fourniture>> fournitureItemWriter) {

        ItemProcessor<Commande, List<Fourniture>> itemProcessor = processCommand::process;

        return new StepBuilder("retrieveCommand", jobRepository)
                .<Commande, List<Fourniture>>chunk(10, transactionManager)
                .reader(kafkaItemReader)
                .processor(itemProcessor)
                .writer(fournitureItemWriter)
                .build();
    }
}
