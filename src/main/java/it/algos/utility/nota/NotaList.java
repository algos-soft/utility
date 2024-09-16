package it.algos.utility.nota;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.backend.annotation.AViewList;
import it.algos.vbase.backend.enumeration.LogLevel;
import it.algos.vbase.backend.enumeration.TypeLog;
import it.algos.vbase.backend.list.CrudList;
import it.algos.vbase.ui.wrapper.ASpan;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
@AViewList()
public class NotaList extends CrudList {
    private ComboBox comboTypeLog;
    private ComboBox comboTypeLevel;


    //--non utilizzato. Serve SOLO per evitare un bug di IntelliJIDEA che segnala errore.
    public NotaList() {
        super();
    }

    /**
     * @param parentCrudView che crea questa istanza
     */
    public NotaList(final NotaView parentCrudView) {
        super(parentCrudView);
    }


    @Override
    public void fixHeader() {
        super.infoScopo = "Appunti liberi";
        message="Data iniziale proposta quella attuale ma modificabile. Data finale inserita automaticamente col flag fatto=true.";
        headerPlaceHolder.add(ASpan.text(message).rosso().small());
        message="Filtri selezione per typeLog e typeLevel. Ordinamento decrescente per data iniziale. Descrizione libera.";
        headerPlaceHolder.add(ASpan.text(message).rosso().small());

        super.fixHeader();
    }

    @PostConstruct
    private void init(){

        comboTypeLog = new ComboBox<>();
        comboTypeLog.setPlaceholder("TypeLog...");
        comboTypeLog.setClearButtonVisible(true);
        comboTypeLog.setWidth("10rem");
        comboTypeLog.setItems(TypeLog.values());
//        comboTypeLog.addValueChangeListener(event -> sync());
        listToolbar.add(comboTypeLog);

        comboTypeLevel = new ComboBox<>();
        comboTypeLevel.setPlaceholder("TypeLevel...");
        comboTypeLevel.setClearButtonVisible(true);
        comboTypeLevel.setWidth("8.7rem");
        comboTypeLevel.setItems(LogLevel.values());
//        comboTypeLevel.addValueChangeListener(event -> sync());
        listToolbar.add(comboTypeLevel);

    }


    @Override
    protected void syncFiltri() {
        if (comboTypeLog != null) {
            if (comboTypeLog.getValue() != null) {
                if (comboTypeLog.getValue() instanceof TypeLog type) {
                    filtri.uguale("typeLog", type);
                }
            }
            else {
                filtri.remove("typeLog");
            }
        }

        if (comboTypeLevel != null) {
            if (comboTypeLevel.getValue() != null) {
                if (comboTypeLevel.getValue() instanceof LogLevel type) {
                    filtri.uguale("typeLevel", type);
                }
            }
            else {
                filtri.remove("typeLevel");
            }
        }
    }


}// end of CrudList class
