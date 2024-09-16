package it.algos.utility.nota;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.backend.entity.AbstractEntity;
import it.algos.vbase.backend.enumeration.CrudOperation;
import it.algos.vbase.backend.list.AList;
import it.algos.vbase.ui.form.CrudForm;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NotaForm  <T extends NotaEntity> extends CrudForm {


    //--non utilizzato. Serve SOLO per evitare un bug di IntelliJIDEA che segnala errore.
    public NotaForm() {
        super();
    }

    //--new entityBean and update existing entityBean
    public NotaForm(final AList parentAList, AbstractEntity entityBean, CrudOperation operation) {
        super(parentAList, entityBean, operation);
    }


    @Override
    protected void override() {
        //--qui eventuali regolazioni specifiche delle variabili
    }

    /**
     * Aggiunge i componenti grafici al layout
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
    protected void addFields() {
        super.addFields();

        if (crudOperation == CrudOperation.create) {
            mappaFields.get("fatto").setEnabled(false);
        }
        else {
            mappaFields.get("typeLog").setEnabled(false);
            mappaFields.get("typeLevel").setEnabled(false);
            mappaFields.get("inizio").setEnabled(false);
            mappaFields.get("typeLog").setEnabled(false);
            mappaFields.get("descrizione").setEnabled(!((Checkbox) mappaFields.get("fatto")).getValue());
        }
    }

}// end of CrudForm class
