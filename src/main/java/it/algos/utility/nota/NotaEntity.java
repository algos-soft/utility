package it.algos.utility.nota;

import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.enumeration.LogLevel;
import it.algos.vbase.backend.enumeration.TypeField;
import it.algos.vbase.backend.enumeration.TypeLog;
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
@ALog()
@AReset()
@AEntity( sort = "inizio", sortDiscendente = true)
public class NotaEntity extends AbstractEntity {

    @AFieldList()
//    @AField(type = TypeField.enumType, enumClazz = TypeLog.class)
    @AFieldForm(type = TypeField.enumType)
    private TypeLog typeLog;

    @AFieldList()
//    @AField(type = TypeField.enumType, enumClazz = LogLevel.class)
    @AFieldForm(type = TypeField.enumType)
    private LogLevel typeLevel;

    @AFieldList()
//    @AField(type = TypeField.localDate, typeDate = TypeDate.dateNormal)
    @AFieldForm(type = TypeField.localDate)
    private LocalDate inizio;

    @AFieldList(width = 20)
    private String descrizione;

    @AFieldList(headerIcon = VaadinIcon.CHECK)
    @AFieldForm(type = TypeField.booleano)
    private boolean fatto;

    @AFieldList()
//    @AField(type = TypeField.localDate, typeDate = TypeDate.dateNormal)
    @AFieldForm(type = TypeField.localDate)
    private LocalDate fine;

    @Override
    public String toString() {
        return inizio != null ? DateTimeFormatter.ofPattern("d-MMM-yy").format(inizio) : VUOTA;
    }

}// end of Entity class
