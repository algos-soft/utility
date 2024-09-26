package it.algos.utility.nota;

import it.algos.vbase.backend.enumeration.LogLevel;
import it.algos.vbase.backend.enumeration.RisultatoReset;
import it.algos.vbase.backend.enumeration.TypeLog;
import it.algos.vbase.backend.logic.ModuloService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static it.algos.vbase.backend.boot.BaseCost.VUOTA;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: gio, 02-nov-2023
 * Time: 09:14
 */
@Service
public class NotaService extends ModuloService<NotaEntity> {

    /**
     * Costruttore invocato dalla sottoclasse concreta obbligatoriamente con due parametri <br>
     * Regola la entityClazz associata a questo Modulo <br>
     * Regola la viewClazz @Route associata a questo Modulo <br>
     */
    public NotaService() {
        super(NotaEntity.class, NotaView.class);
    }


    /**
     * Regola le property visibili in una scheda CrudForm <br>
     * Di default prende tutti i fields della ModelClazz specifica <br>
     * Può essere sovrascritto SENZA richiamare il metodo della superclasse <br>
     */
    public List<String> getFormPropertyNames() {
        return Arrays.asList("typeLog", "typeLevel", "inizio", "descrizione", "fatto");
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public NotaEntity newEntity() {
        return newEntity(null, null, null, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param typeLog     merceologico della nota
     * @param typeLevel   di importanza o rilevanza della nota
     * @param descrizione dettagliata della nota
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public NotaEntity newEntity(TypeLog typeLog, LogLevel typeLevel, LocalDate inizio, String descrizione) {
        NotaEntity newEntityBean = NotaEntity.builder()
                .typeLog(typeLog == null ? TypeLog.system : typeLog)
                .typeLevel(typeLevel == null ? LogLevel.info : typeLevel)
                .inizio(inizio != null ? inizio : LocalDate.now())
                .descrizione(descrizione != null ? descrizione : null)
                .fatto(false)
                .build();

        return newEntityBean;
    }


    @Override
    public Sort getBasicSort() {
        return Sort.by(Sort.Order.asc("fatto"), Sort.Order.desc("evento"));
    }




    @Override
    public RisultatoReset reset() {
        RisultatoReset typeReset = RisultatoReset.nessuno;

        if (collectionNullOrEmpty()) {
            insert(newEntity(TypeLog.debug,LogLevel.warn,LocalDate.now(),"Prima nota (da cancellare)"));
            typeReset = RisultatoReset.vuotoMaCostruito;
        } else {
            typeReset = RisultatoReset.esistenteNonModificato;
        }

        return typeReset;
    }

}// end of CrudService class
