package fr.example;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.model.Fourniture;
import com.example.saver.SaveFourniture;
import com.example.saver.Saver;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class FournitureSaver implements SaveFourniture {

    private final Saver saver;

    @Override
    public void save(@Nonnull List<Fourniture> fournitures) {
        requireNonNull(fournitures, () -> "La liste des fournitures ne doit pas être null");

        saver.save(
                fournitures,
                /*language=PostgreSQL*/
                """
                        insert into fourniture (nom, prix_ht, fournisseur) values (?, ?, ?)
                        """,
                (ps, argument) -> {
                    ps.setString(1, argument.nom());
                    ps.setBigDecimal(2, argument.prixHT());
                    ps.setString(3, argument.fournisseur().nom());
                }
        );
    }
}
