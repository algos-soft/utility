package it.algos.utility.nota;

import com.vaadin.flow.data.provider.SortDirection;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.enumeration.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static it.algos.vbase.backend.boot.BaseCost.VUOTA;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "nota")
@AReset()
@AEntity(sortProperty = "inizio", sortDirection = SortDirection.DESCENDING)
public class NotaEntity extends AbstractEntity {

    @ASearch()
    @AFieldForm(label = "typeColor", clearButtonVisible = TBool.vero)
    private TypeColor colore;

    @NotNull
    @ASearch()
    private TypeLog typeLog;

    @NotNull
    @ASearch()
    private LogLevel typeLevel;

    @NotNull
    private LocalDate inizio;

    @NotBlank
    @ASearch(textSearchMode = TextSearchMode.contains)
    @AFieldList(width = 30)
    @AFieldForm(width = 40)
    private String descrizione;

    @ASearch(checkBoxInitialStatus = CheckBoxStatus.falso)
    private boolean fatto;

    private LocalDate fine;

    @Override
    public String toString() {
        return inizio != null ? DateTimeFormatter.ofPattern("d-MMM-yy").format(inizio) : VUOTA;
    }

}// end of Entity class
