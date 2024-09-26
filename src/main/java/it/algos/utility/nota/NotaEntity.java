package it.algos.utility.nota;

import com.vaadin.flow.data.provider.SortDirection;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.enumeration.*;
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

    @ASearch()
    private TypeLog typeLog;

    @ASearch()
    private LogLevel typeLevel;

    private LocalDate inizio;

    @ASearch(textSearchMode = TextSearchMode.contains)
    @AFieldList(width = 30)
    @AFieldForm(width = 30)
    private String descrizione;

    @ASearch(checkBoxInitialStatus = CheckBoxStatus.falso)
    private boolean fatto;

    private LocalDate fine;

    @Override
    public String toString() {
        return inizio != null ? DateTimeFormatter.ofPattern("d-MMM-yy").format(inizio) : VUOTA;
    }

}// end of Entity class
