package fr.example.config;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.model.Commande;

@TestConfiguration
public class ProducerConfig {

    @Bean
    KafkaTemplate<String, Commande> kafkaTemplate(KafkaProperties kafkaProperties) {

        var producerProps = kafkaProperties.buildProducerProperties(null);
        producerProps.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        var pfs = new DefaultKafkaProducerFactory<String, Commande>(producerProps);
        return new KafkaTemplate<>(pfs);
    }
}
