package it.algos.utility.nota;

import com.vaadin.flow.data.provider.SortDirection;
import it.algos.vbase.annotation.*;
import it.algos.vbase.entity.AbstractEntity;
import it.algos.vbase.enumeration.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static it.algos.vbase.boot.BaseCost.*;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "nota")
@IReset()
@IEntity(sortProperty = "inizio", sortDirection = SortDirection.DESCENDING)
public class NotaEntity extends AbstractEntity {

    @IFieldSearch()
    @IFieldForm(label = "typeColor", clearButtonVisible = TBool.vero)
    private TypeColor colore;

    @NotNull(message = NO_NULL)
    @IFieldSearch()
    private TypeLog typeLog;

    @NotNull(message = NO_NULL)
    @IFieldSearch()
    private LogLevel typeLevel;

    @NotNull
    private LocalDate inizio;

    @NotBlank(message = NO_BLANK)
    @IFieldSearch(textSearchMode = TextSearchMode.contains)
    @IFieldList(width = 30)
    @IFieldForm(width = 40)
    private String descrizione;

    @IFieldSearch(checkBoxInitialStatus = TriState.falso)
    private boolean fatto;

    private LocalDate fine;

    @Override
    public String toString() {
        return inizio != null ? DateTimeFormatter.ofPattern("d-MMM-yy").format(inizio) : VUOTA;
    }

}// end of Entity class
