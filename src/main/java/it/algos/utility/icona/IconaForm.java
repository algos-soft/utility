package it.algos.utility.icona;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.components.SimpleHorizontalLayout;
import it.algos.vbase.components.SimpleVerticalLayout;
import it.algos.vbase.form.DefaultForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * Project wam24
 * Created by Algos
 * User: gac
 * Date: mer, 12-mar-2025
 * Time: 10:31
 * <p>
 */
@Slf4j
@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
public class IconaForm extends DefaultForm<IconaEntity> {

    private ComboBox<VaadinIcon> comboBoxIcona;


    public IconaForm(IconaEntity bean) {
        super(bean);
    }


    @Override
    protected void addFieldsToLayout() {
        VerticalLayout layout = new SimpleVerticalLayout();
        layout.setSizeFull();
        AbstractField<?, ?> field;
        TextField desc;

        HorizontalLayout primaRiga = new SimpleHorizontalLayout();
        field = getField("ordine");
        primaRiga.add(field);

        //field escluso dal binder - occorre inserire e recuperare il valore
        primaRiga.add(creaComboIcona());
        layout.add(primaRiga);

        this.add(layout);
    }


    protected ComboBox<VaadinIcon> creaComboIcona() {
        comboBoxIcona = new ComboBox<>("Icone base di Vaadin");
        comboBoxIcona.setWidth("18rem");

        VaadinIcon[] items = VaadinIcon.values();
        comboBoxIcona.setItems(items);
        VaadinIcon value;

        // Renderer per il menu a tendina (Dropdown)
        comboBoxIcona.setRenderer(new ComponentRenderer<>(vaadinIcon -> {
            HorizontalLayout layout = new HorizontalLayout();
            layout.setAlignItems(FlexComponent.Alignment.CENTER);

            Icon icon = creaIcona(vaadinIcon);  // Usa il metodo di creazione icona
            Span text = new Span(vaadinIcon.name());

            layout.add(icon, text);
            return layout;
        }));

        // Assegna un listener per aggiornare il valore selezionato senza duplicare il testo
        comboBoxIcona.addValueChangeListener(event ->
                aggiornaValoreSelezionato(comboBoxIcona, event.getValue()));

        // Imposta un valore iniziale
        value = bean != null ? bean.getVaadinIcon() : null;
        comboBoxIcona.setValue(value);

        return comboBoxIcona;
    }


    private void aggiornaValoreSelezionato(ComboBox<VaadinIcon> comboBox, VaadinIcon valore) {
        if (valore != null) {
            Icon icon = creaIcona(valore);

            // Avvolgere l'icona in un Div per mantenerne la dimensione corretta
            Div iconWrapper = new Div(icon);
            iconWrapper.getStyle().set("display", "flex").set("align-items", "center");

            comboBox.setPrefixComponent(iconWrapper);
            super.saveBtn.setEnabled(comboBox.getValue() == valore);
        } else {
            comboBox.setPrefixComponent(null);
        }
    }

    // Metodo per creare un'icona a partire da un'istanza di IconaEntity
    private Icon creaIcona(VaadinIcon vaadinIcon) {
        Icon icon = vaadinIcon.create();
        icon.setSize("1.25rem");
        icon.getStyle().set("margin-right", "0.5rem");
        icon.getStyle().set("color", "blue");
        return icon;
    }


    protected IconaEntity persistBean(IconaEntity bean) {
        VaadinIcon vaadinIcon = comboBoxIcona.getValue();
        bean.setVaadinIcon(vaadinIcon);
        return super.persistBean(bean);
    }

}