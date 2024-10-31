package fr.example;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.model.Fourniture;
import com.example.saver.SaveFourniture;
import com.example.saver.Saver;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FournitureSaver implements SaveFourniture {

    private final Saver saver;

    @Override
    public void save(Fourniture fourniture) {
        saver.save(
                fourniture,
                /*language=PostgreSQL*/
                """
                        insert into fourniture (nom, prix_ht, fournisseur) values (:nom, :prixHT, :nomFournisseur)
                        """,
                Map.of(
                        "nom", fourniture.nom(),
                        "prixHT", fourniture.prixHT(),
                        "nomFournisseur", fourniture.fournisseur().nom()
                ));
    }
}
