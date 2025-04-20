package it.algos.utility.wamicona;

import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vbase.enumeration.RisultatoReset;
import it.algos.vbase.service.ModuloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project wam24
 * Created by Algos
 * User: gac
 * Date: mer, 12-mar-2025
 * Time: 10:31
 * <p>
 * Service di una entityClazz specifica e di un package <br>
 * Garantisce i metodi di collegamento per accedere al database <br>
 * Non mantiene lo stato di una istanza entityBean <br>
 */
@Slf4j
@Service
public class IconaService extends ModuloService<IconaEntity> {


    /**
     * Regola la entityClazz (final) associata a questo service <br>
     */
    public IconaService() {
        super(IconaEntity.class);
    }


    public IconaEntity newEntity(VaadinIcon vaadinIcon) {
        return newEntity(0, vaadinIcon);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param ordine     (obbligatoria)
     * @param vaadinIcon (obbligatoria)
     * @return la nuova entity appena creata (ma non salvata)
     */
    public IconaEntity newEntity(int ordine, VaadinIcon vaadinIcon) {
        IconaEntity newEntityBean = IconaEntity.builder()
                .ordine(ordine == 0 ? nextOrdine() : ordine)
                .vaadinIcon(vaadinIcon)
                .build();

        return newEntityBean;
    }

    public IconaEntity findByVaadinIcon(VaadinIcon vaadinIcon) {
        List<IconaEntity> lista = findAll();
        return lista.stream()
                .filter(iconaEntity -> iconaEntity.getVaadinIcon().equals(vaadinIcon))
                .findFirst()
                .orElse(null);
    }

    //--chiamato da startup
    @Override
    public RisultatoReset reset() {
        return reset(this.getMongoTemplate());
    }

    @Override
    public RisultatoReset reset(MongoTemplate mongoTemplate) {
        return resetOnly(mongoTemplate);
    }

    @Override
    public RisultatoReset resetStartup(MongoTemplate mongoTemplate) {
        return resetOnly(mongoTemplate);
    }

    public RisultatoReset resetOnly(MongoTemplate mongoTemplate) {
        deleteAll();

        save(newEntity(VaadinIcon.AMBULANCE), mongoTemplate);
        save(newEntity(VaadinIcon.HEART), mongoTemplate);
        save(newEntity(VaadinIcon.MEDAL), mongoTemplate);
        save(newEntity(VaadinIcon.STETHOSCOPE), mongoTemplate);
        save(newEntity(VaadinIcon.USER), mongoTemplate);
        save(newEntity(VaadinIcon.USER_STAR), mongoTemplate);
        save(newEntity(VaadinIcon.TRUCK), mongoTemplate);
        save(newEntity(VaadinIcon.MALE), mongoTemplate);
        save(newEntity(VaadinIcon.FEMALE), mongoTemplate);
        save(newEntity(VaadinIcon.PHONE), mongoTemplate);
        save(newEntity(VaadinIcon.DOCTOR), mongoTemplate);
        save(newEntity(VaadinIcon.SPECIALIST), mongoTemplate);
        save(newEntity(VaadinIcon.BED), mongoTemplate);
        save(newEntity(VaadinIcon.OFFICE), mongoTemplate);
        save(newEntity(VaadinIcon.BRIEFCASE), mongoTemplate);
        save(newEntity(VaadinIcon.STAR), mongoTemplate);
        save(newEntity(VaadinIcon.USER_HEART), mongoTemplate);
        save(newEntity(VaadinIcon.USER_CLOCK), mongoTemplate);
        save(newEntity(VaadinIcon.USER_CHECK), mongoTemplate);

        return null;
    }


}// end of Singleton class
