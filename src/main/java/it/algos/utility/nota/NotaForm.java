package it.algos.utility.nota;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.components.SimpleHorizontalLayout;
import it.algos.vbase.enumeration.LogLevel;
import it.algos.vbase.enumeration.TypeLog;
import it.algos.vbase.field.ADateField;
import it.algos.vbase.field.ATextField;
import it.algos.vbase.form.DefaultForm;
import it.algos.vbase.mongo.MongoTemplateProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDate;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class NotaForm extends DefaultForm<NotaEntity> {

    @Autowired
    protected MongoTemplateProvider mongoTemplateProvider;


    public NotaForm(NotaEntity bean) {
        super(bean);
    }

    protected void preInit() {
        if (newRecord) {
            bean.setTypeLog(TypeLog.sviluppo);
            bean.setTypeLevel(LogLevel.info);
            bean.setInizio(LocalDate.now());
        }
    }

    @Override
    protected void addFieldsToLayout() {
        AbstractField<?, ?> field;
        HorizontalLayout primaRiga = new SimpleHorizontalLayout();
        primaRiga.setWidthFull();
        primaRiga.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        Div spacer = new Div();
        spacer.getStyle().set("flex-grow", "1");

        primaRiga.add(getField("typeLog"));
        primaRiga.add(getField("typeLevel"));
        primaRiga.add(spacer);
        primaRiga.add(getField("inizio"));
        add(primaRiga);

        ATextField textField = (ATextField) getField("descrizione");
        textField.focus();
        add(textField);

        HorizontalLayout terzaRiga = new SimpleHorizontalLayout();
        terzaRiga.setAlignItems(Alignment.BASELINE);
        terzaRiga.setWidthFull();
        field = getField("fatto");
        field.getElement().getStyle().set("margin-top", "0.75rem");
        terzaRiga.add(field);

        if (!newRecord && bean.isFatto()) {
            Div spacer2 = new Div();
            spacer2.getStyle().set("flex-grow", "1");
            terzaRiga.add(spacer2);
            ADateField dateField = (ADateField) getField("fine");
            terzaRiga.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
            terzaRiga.add(dateField);
        }
        add(terzaRiga);
    }


    @Override
    public void writeBean(NotaEntity newBean) throws ValidationException {
        super.writeBean(newBean);

        if (!newRecord) {
            NotaEntity oldBean = getMongoTemplate().findById(newBean.getId(), NotaEntity.class);
            if (oldBean.getFine() == null && ((NotaEntity) newBean).isFatto()) {
                ((NotaEntity) newBean).setFine(LocalDate.now());
            }
        }
    }

    protected MongoTemplate getMongoTemplate(){
        return mongoTemplateProvider.getMongoTemplate();
    }

}