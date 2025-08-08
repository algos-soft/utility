package it.algos.utility.icona;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vbase.annotation.IEntity;
import it.algos.vbase.annotation.IFieldForm;
import it.algos.vbase.annotation.IFieldList;
import it.algos.vbase.annotation.IReset;
import it.algos.vbase.entity.AbstractEntity;
import it.algos.vbase.field.ComboComponent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.aot.hint.annotation.Reflective;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Project wam24
 * Created by Algos
 * User: gac
 * Date: mer, 12-mar-2025
 * Time: 10:31
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 */
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "icona")
@IReset()
@IEntity(sortProperty = "ordine", singularName = "icona", pluralName = "Icone")
public class IconaEntity extends AbstractEntity {


    @Indexed()
    @Positive
    @IFieldList(width = 5, headerIcon = VaadinIcon.HASH, headerIconTooltip = "Ordine logico")
    @IFieldForm(width = 4)
    private int ordine;


    @Indexed(unique = true)
    @NotNull
    @IFieldList(headerText = "Icon", sortable = false)
    @IFieldForm(excluded = true)
    private VaadinIcon vaadinIcon;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return vaadinIcon.name();
    }


    @JsonIgnore
    public Component renderList() {
        Span span = new Span();

        if (vaadinIcon != null) {
            Icon icon = vaadinIcon.create();
            icon.setSize("20px");
            icon.getStyle().set("margin-right", "8px");
            icon.getStyle().set("color", "blue");
            span.add(icon);
        }

        return span;
    }

    @Reflective
    public ComboComponent renderForm() {
        Span tondo = new Span();
        tondo.getStyle()
                .set("display", "inline-block")
                .set("width", "20px")
                .set("height", "20px")
                .set("background-color", "blue") // Usa il nome del colore come background
                .set("margin-right", "8px")
                .set("border-radius", "50%"); // Rende il quadrato un cerchio

        return new ComboComponent(tondo, "html");
    }

}// end of Bean