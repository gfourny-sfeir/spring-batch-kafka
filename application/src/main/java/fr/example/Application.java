package fr.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.Write;
import com.example.filter.CommandFilterer;
import com.example.filter.FilterCommand;
import com.example.saver.SaveFourniture;
import com.example.saver.Saver;
import com.example.transformer.CommandTransformer;
import com.example.transformer.TransformCommand;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    FilterCommand processCommand() {
        return new CommandFilterer();
    }

    @Bean
    TransformCommand transformCommand() {
        return new CommandTransformer();
    }

    @Bean
    SaveFourniture<?> saveFourniture(Saver saver) {
        return new FournitureSaver(saver);
    }

    @Bean
    Write writer(FileWriter fileWriter) {
        return fileWriter::write;
    }
}
