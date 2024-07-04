package it.algos.utility.logs;

import it.algos.vbase.backend.annotation.AEntity;
import it.algos.vbase.backend.annotation.AFieldList;
import it.algos.vbase.backend.entity.AbstractCompany;
import it.algos.vbase.backend.enumeration.LogLevel;
import it.algos.vbase.backend.enumeration.TypeLog;
import it.algos.vbase.backend.modules.utente.UtenteEntity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static it.algos.vbase.backend.boot.BaseCost.VUOTA;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "logger")
@AEntity(sort = "evento", sortDiscendente = true)
public class LogEntity extends AbstractCompany {

    /**
     * utente (obbligatorio) <br>
     */
    @DBRef
    @AFieldList()
//    @AField(type = TypeField.linkDBRef, linkClazz = UtenteEntity.class)
    private UtenteEntity utente;

    /**
     * modulo (obbligatorio) <br>
     */
    @AFieldList()
    private String modulo;

    /**
     * raggruppamento logico dei log per type di eventi (obbligatorio)
     */
    @AFieldList()
//    @AField(type = TypeField.enumType, enumClazz = TypeLog.class)
    private TypeLog typeLog;

    /**
     * raggruppamento logico dei log per livello di eventi (obbligatorio)
     */
    @AFieldList(width = 8)
//    @AField(type = TypeField.enumType, enumClazz = LogLevel.class)
    private LogLevel typeLevel;

    /**
     * Data dell'evento (obbligatoria, non modificabile)
     * Gestita in automatico
     * Field visible solo per admin
     */
    @AFieldList(width = 10)
//    @AField(type = TypeField.localDateTime, typeDate = TypeDate.normaleOrario)
    private LocalDateTime evento;

    @AFieldList(width = 20)
    private String modifica;

    /**
     * descrizione (facoltativa, non unica) <br>
     */
    @AFieldList(width = 40, visible = false)
//    @AField(type = TypeField.textArea)
    private String descrizione;


    @Override
    public String toString() {
        return evento != null ? evento.format(DateTimeFormatter.ofPattern("d-MMM-yy H:mm", Locale.ITALIAN)) : VUOTA;
    }

}// end of Entity class
