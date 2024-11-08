package it.algos.utility.nota;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.components.SimpleHorizontalLayout;
import it.algos.vbase.entity.AbstractEntity;
import it.algos.vbase.enumeration.LogLevel;
import it.algos.vbase.enumeration.TypeLog;
import it.algos.vbase.form.DefaultForm;
import it.algos.vbase.service.LoggerService;
import it.algos.vbase.wrapper.WrapField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NotaForm<T extends AbstractEntity> extends DefaultForm<T> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    protected LoggerService logger;


    public NotaForm(T bean) {
        super(bean);
    }


    @Override
    public List<WrapField> registerFields() {
        List<String> propertyNames = new ArrayList<>(Arrays.asList("colore", "typeLog", "typeLevel", "inizio", "descrizione", "fatto"));

        if (newRecord) {
            ((NotaEntity) bean).setTypeLog(TypeLog.system);
            ((NotaEntity) bean).setTypeLevel(LogLevel.info);
            ((NotaEntity) bean).setInizio(LocalDate.now());
        } else {
            propertyNames.add("fine");
        }

//        return formService.creaFieldsList(getBeanType(), propertyNames);
        return null;
    }

    @Override
    protected void addFieldsToLayout() {
        HorizontalLayout primaRiga = new SimpleHorizontalLayout();
        primaRiga.setAlignItems(Alignment.BASELINE);
        primaRiga.add(getField("colore"));
        primaRiga.add(getField("typeLog"));
        primaRiga.add(getField("typeLevel"));
        add(primaRiga);

        add(getField("inizio"));

        HorizontalLayout terzaRiga = new SimpleHorizontalLayout();
        terzaRiga.setAlignItems(Alignment.BASELINE);
        terzaRiga.add(getField("descrizione"));
        terzaRiga.add(getField("fatto"));
        add(terzaRiga);

        if (!newRecord && ((NotaEntity) bean).isFatto()) {
            add(getField("fine"));
        }
    }


    @Override
    public void writeBean(T newBean) throws ValidationException {
        super.writeBean(newBean);

        if (!newRecord) {
            NotaEntity oldBean = mongoTemplate.findById(newBean.getId(), NotaEntity.class);
            if (oldBean.getFine() == null && ((NotaEntity) newBean).isFatto()) {
                ((NotaEntity) newBean).setFine(LocalDate.now());
            }
        }

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


}