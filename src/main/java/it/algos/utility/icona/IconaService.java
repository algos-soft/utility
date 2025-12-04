package it.algos.utility.icona;

import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vbase.enumeration.RisultatoReset;
import it.algos.vbase.service.ModuloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
        if ( mongoTemplate.count(new Query(), IconaEntity.class)>0) {
            return null;
        }

        mongoTemplate.remove(new Query(), IconaEntity.class);

        AtomicInteger counter = new AtomicInteger(1);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.AMBULANCE), mongoTemplate);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.HEART), mongoTemplate);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.MEDAL), mongoTemplate);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.STETHOSCOPE), mongoTemplate);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.USER), mongoTemplate);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.USER_STAR), mongoTemplate);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.TRUCK), mongoTemplate);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.MALE), mongoTemplate);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.FEMALE), mongoTemplate);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.PHONE), mongoTemplate);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.DOCTOR), mongoTemplate);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.SPECIALIST), mongoTemplate);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.BED), mongoTemplate);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.OFFICE), mongoTemplate);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.BRIEFCASE), mongoTemplate);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.STAR), mongoTemplate);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.USER_HEART), mongoTemplate);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.USER_CLOCK), mongoTemplate);
        save(newEntity(counter.getAndIncrement(),VaadinIcon.USER_CHECK), mongoTemplate);

        return null;
    }


}// end of Singleton class
