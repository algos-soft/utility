package it.algos.utility.logs;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vbase.backend.annotation.AList;
import it.algos.vbase.backend.enumeration.LogLevel;
import it.algos.vbase.backend.enumeration.TypeLog;
import it.algos.vbase.backend.list.CrudCompanyList;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@SpringComponent
@Scope(value = SCOPE_PROTOTYPE)
@AList()
public class LogList extends CrudCompanyList {

    private ComboBox comboModulo;

    private ComboBox comboTypeLog;

    private ComboBox comboTypeLevel;


//    //--non utilizzato. Serve SOLO per evitare un bug di IntelliJIDEA che segnala errore.
//    public LogList() {
//        super();
//    }

    /**
     * @param parentCrudView che crea questa istanza
     */
    public LogList(final LogView parentCrudView) {
        super(parentCrudView);
    }


    protected void fixPreferenze() {
        super.fixPreferenze();

//        super.usaBottoneDownload = false;
//        super.usaBottoneNew = false;
        super.usaBottoneEdit = false;
        super.usaBottoneShow = true;
        super.usaVariantCompact = true;
        super.usaColumnWrap = true;
        super.usaSelettoreColonne = true;
    }


    @PostConstruct
    private void init() {
        comboModulo = new ComboBox<>();
        comboModulo.setPlaceholder("Modulo...");
        comboModulo.setClearButtonVisible(true);
        comboModulo.setWidth("10rem");
        comboModulo.setItems(getModuliLoggabili());
        comboModulo.addValueChangeListener(event -> sync());
        listToolbar.add(comboModulo);

        comboTypeLog = new ComboBox<>();
        comboTypeLog.setPlaceholder("TypeLog...");
        comboTypeLog.setClearButtonVisible(true);
        comboTypeLog.setWidth("10rem");
        comboTypeLog.setItems(TypeLog.values());
        comboTypeLog.addValueChangeListener(event -> sync());
        listToolbar.add(comboTypeLog);

        comboTypeLevel = new ComboBox<>();
        comboTypeLevel.setPlaceholder("TypeLevel...");
        comboTypeLevel.setClearButtonVisible(true);
        comboTypeLevel.setWidth("8.7rem");
        comboTypeLevel.setItems(LogLevel.values());
        comboTypeLevel.addValueChangeListener(event -> sync());
        listToolbar.add(comboTypeLevel);
    }

    protected List<String> getModuliLoggabili() {
        return reflectionService.getSubClazzEntity()
                .stream()
                .filter(clazz -> annotationService.usaLogModifiche(clazz))
                .map(clazz -> mongoTemplate.getCollectionName(clazz))
                .collect(Collectors.toList());
    }

    @Override
    protected void syncFiltri() {
        if (comboModulo != null) {
            if (comboModulo.getValue() != null) {
                if (comboModulo.getValue() instanceof String nomeModulo) {
                    filtri.uguale("modulo", nomeModulo);
                }
            }
            else {
                filtri.remove("modulo");
            }
        }

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
