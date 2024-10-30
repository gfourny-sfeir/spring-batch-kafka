package fr.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.processor.CommandProcessor;
import com.example.processor.ProcessCommand;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    ProcessCommand processCommand(){
        return new CommandProcessor();
    }
}
