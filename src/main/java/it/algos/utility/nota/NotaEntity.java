package it.algos.utility.nota;

import com.vaadin.flow.data.provider.SortDirection;
import it.algos.vbase.annotation.*;
import it.algos.vbase.entity.AbstractEntity;
import it.algos.vbase.enumeration.LogLevel;
import it.algos.vbase.enumeration.TextSearchMode;
import it.algos.vbase.enumeration.TriState;
import it.algos.vbase.enumeration.TypeLog;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static it.algos.vbase.boot.BaseCost.NO_BLANK;
import static it.algos.vbase.boot.BaseCost.VUOTA;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "nota")
@IReset()
@IEntity(sortProperty = "inizio", sortDirection = SortDirection.DESCENDING)
public class NotaEntity extends AbstractEntity {

    @Transient
    private static final int LAR = 8;
    private static final int LAR_UNO = 10;
    private static final int LAR_TRE = 40;


    @NotNull()
    @IFieldSearch(textSearchMode = TextSearchMode.startsWith, linkedProperty = "tag")
    @IFieldList(width = LAR_UNO)
    private TypeLog typeLog;

    @NotNull()
    @IFieldSearch(textSearchMode = TextSearchMode.startsWith)
    @IFieldList(width = LAR_UNO)
    private LogLevel typeLevel;

    @NotNull
    @IFieldList(width = LAR)
    private LocalDate inizio;

    @NotBlank(message = NO_BLANK)
    @IFieldSearch(textSearchMode = TextSearchMode.contains)
    @IFieldList(width = LAR_UNO, grow = 1)
    @IFieldForm(width = LAR_TRE)
    private String descrizione;

    @IFieldSearch(checkBoxInitialStatus = TriState.falso)
    private boolean fatto;

    @IFieldList(width = LAR)
    private LocalDate fine;

    @Override
    public String toString() {
        return inizio != null ? DateTimeFormatter.ofPattern("d-MMM-yy").format(inizio) : VUOTA;
    }

}// end of Entity class
