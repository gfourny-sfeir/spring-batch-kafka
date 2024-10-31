package fr.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.filter.CommandFilterer;
import com.example.filter.FilterCommand;
import com.example.model.Fourniture;
import com.example.saver.FileSaver;
import com.example.saver.SaveFourniture;
import com.example.saver.Saver;
import com.example.transformer.CommandTransformer;
import com.example.transformer.TransformCommand;
import com.example.writer.WriteFile;

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
    WriteFile<?, ?> writeFile(FileSaver fileSaver) {
        return new FileWriter(fileSaver);
    }

    @Bean
    SaveFourniture<?> saveFourniture(Saver saver) {
        return new FournitureSaver(saver);
    }
}
