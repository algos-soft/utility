package it.algos.utility.preferenza;

import com.vaadin.flow.component.icon.*;
import it.algos.vbase.backend.annotation.*;
import it.algos.vbase.backend.entity.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.pref.*;
import lombok.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "preferenza")
@AEntity
public class PreferenzaEntity extends AbstractEntity {

    @Indexed(unique = true)
    @ASearch(type = TypeSearch.textStartsWith)
    @AFieldList(width = 16)
//    @AField(type = TypeField.text)
    private String code;

    @ASearch(type = TypeSearch.comboClazz, enumClazz = TypePref.class)
//    @AField(type = TypeField.enumType, enumClazz = TypePref.class)
    private TypePref type;

    @ASearch(type = TypeSearch.textContains)
    @AFieldList(width = 30)
//    @AField(type = TypeField.text)
    private String descrizione;

    @AFieldList(width = 12)
//    @AField(type = TypeField.preferenza)
    private byte[] iniziale;

    @AFieldList(width = 12)
//    @AField(type = TypeField.preferenza)
    private byte[] corrente;

    @Indexed()
    @ASearch(type = TypeSearch.checkBox)
    @AFieldList(headerIcon = VaadinIcon.FIRE)
//    @AField(type = TypeField.booleano)
    private boolean critical;

    @Indexed()
    @ASearch(type = TypeSearch.checkBox)
    @AFieldList(headerIcon = VaadinIcon.ASTERISK)
//    @AField(type = TypeField.booleano)
    private boolean dinamica;

    @Indexed()
    @ASearch(type = TypeSearch.checkBox)
    @AFieldList(headerIcon = VaadinIcon.HOME)
//    @AField(type = TypeField.booleano)
    private boolean base24;

    @Override
    public String toString() {
        return code;
    }

}// end of Entity class
