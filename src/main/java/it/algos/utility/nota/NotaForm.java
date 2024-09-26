package it.algos.utility.nota;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.backend.form.DefaultForm;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NotaForm<T extends NotaEntity> extends DefaultForm {


    public NotaForm(T bean) {
        super(bean);
    }



    /**
     * Aggiunge i componenti grafici al layout
     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse <br>
     */
//    protected void addFields() {
//        super.addFields();
//
//        if (crudOperation == CrudOperation.create) {
//            mappaFields.get("fatto").setEnabled(false);
//        } else {
//            mappaFields.get("typeLog").setEnabled(false);
//            mappaFields.get("typeLevel").setEnabled(false);
//            mappaFields.get("inizio").setEnabled(false);
//            mappaFields.get("typeLog").setEnabled(false);
//            mappaFields.get("descrizione").setEnabled(!((Checkbox) mappaFields.get("fatto")).getValue());
//        }
//    }

}// end of CrudForm class
