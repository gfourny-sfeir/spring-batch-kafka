package fr.example;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.filter.FilterCommand;
import com.example.model.Commande;
import com.example.model.Fourniture;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FiltrageCommandeProcessor implements ItemProcessor<Commande, List<Fourniture>> {

    private final FilterCommand filterCommand;

    @Override
    public List<Fourniture> process(@NonNull Commande item) {
        return filterCommand.filter(item);
    }
}
