package it.algos.utility.preferenza;

import ch.carnet.kasparscherrer.*;
import com.vaadin.flow.component.combobox.*;
import com.vaadin.flow.spring.annotation.*;

import it.algos.vbase.backend.annotation.AList;
import it.algos.vbase.backend.list.*;

import static org.springframework.beans.factory.config.BeanDefinition.*;

import it.algos.vbase.ui.wrapper.ASpan;
import org.springframework.context.annotation.*;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
@AList(columns = {"code", "type", "iniziale", "corrente", "descrizione", "critical", "dinamica", "base24"})
public class PreferenzaList extends CrudList {


    private ComboBox comboType;

    private static String FIELD_TYPE = "type";

    private IndeterminateCheckbox boxCritical;

    private IndeterminateCheckbox boxDinamica;

    private IndeterminateCheckbox boxBase24;

    //--non utilizzato. Serve SOLO per evitare un bug di IntelliJIDEA che segnala errore.
    public PreferenzaList() {
        super();
    }

    /**
     * @param parentView che crea questa istanza
     */
    public PreferenzaList(final PreferenzaView parentView) {
        super(parentView);
    }

    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

//        super.setColonneLista("code", "type", "iniziale", "corrente", "descrizione", "critical", "dinamica", "base24");
    }

    @Override
    public void syncHeader() {
        //        super.infoScopo = String.format(typeList.getInfoScopo());
        //        super.fixHeader();
        message = "Di default ordinate per &#8593;base24, &#8593;critical e &#8595;code";
        headerPlaceHolder.add(ASpan.text(message).rosso());

        super.fixHeaderPost();
    }


//    /**
//     * Aggiunge componenti al Top della Lista <br>
//     * Può essere sovrascritto, invocando PRIMA il metodo della superclasse se si vogliono aggiungere componenti IN CODA <br>
//     * Può essere sovrascritto, SENZA invocare il metodo della superclasse se si vogliono aggiungere componenti in ordine diverso <br>
//     */
//    protected void fixTopNo() {
//        super.fixTop();
//
//        Button buttonResetAdd = buttonBar.getButtonResetAdd();
//        buttonResetAdd.getElement().setProperty("title", TEXT_RESET_ADD);
//
//        comboType = new ComboBox<>();
//        comboType.setPlaceholder(TAG_ALTRE_BY + "type");
//        comboType.getElement().setProperty("title", "Filtro di selezione");
//        comboType.setClearButtonVisible(true);
//        comboType.setItems(TypePref.class.getEnumConstants());
//        comboType.addValueChangeListener(event -> sync());
//        buttonBar.add(comboType);
//
//        boxCritical = new IndeterminateCheckbox();
//        boxCritical.setLabel("Critica");
//        boxCritical.setIndeterminate(true);
//        boxCritical.addValueChangeListener(event -> sync());
//        HorizontalLayout layout1 = new HorizontalLayout(boxCritical);
//        layout1.setAlignItems(Alignment.CENTER);
//        buttonBar.add(layout1);
//
//        boxDinamica = new IndeterminateCheckbox();
//        boxDinamica.setLabel("Dinamica");
//        boxDinamica.setIndeterminate(true);
//        boxDinamica.addValueChangeListener(event -> sync());
//        HorizontalLayout layout2 = new HorizontalLayout(boxDinamica);
//        layout2.setAlignItems(Alignment.CENTER);
//        buttonBar.add(layout2);
//
//        boxBase24 = new IndeterminateCheckbox();
//        boxBase24.setLabel("Base24");
//        boxBase24.setIndeterminate(true);
//        boxBase24.addValueChangeListener(event -> sync());
//        HorizontalLayout layout3 = new HorizontalLayout(boxBase24);
//        layout3.setAlignItems(Alignment.CENTER);
//        buttonBar.add(layout3);
//    }


//    protected void syncFiltriNo() {
//        if (comboType != null) {
//            if (comboType.getValue() != null) {
//                if (comboType.getValue() instanceof TypePref type) {
//                    filtri.uguale(FIELD_TYPE, type);
//                }
//            }
//            else {
//                filtri.remove(FIELD_TYPE);
//            }
//        }
//
//        if (boxCritical != null && !boxCritical.isIndeterminate()) {
//            filtri.uguale("critical", boxCritical.getValue());
//        }
//        else {
//            filtri.remove("critical");
//        }
//
//        if (boxDinamica != null && !boxDinamica.isIndeterminate()) {
//            filtri.uguale("dinamica", boxDinamica.getValue());
//        }
//        else {
//            filtri.remove("dinamica");
//        }
//
//        if (boxBase24 != null && !boxBase24.isIndeterminate()) {
//            filtri.uguale("base24", boxBase24.getValue());
//        }
//        else {
//            filtri.remove("base24");
//        }
//
//    }

}// end of CrudList class
