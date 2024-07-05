package it.algos.utility.logs;

import com.mongodb.client.result.DeleteResult;
import it.algos.vbase.backend.enumeration.LogLevel;
import it.algos.vbase.backend.enumeration.TypeLog;
import it.algos.vbase.backend.logic.ModuloService;
import it.algos.vbase.backend.modules.utente.UtenteEntity;
import it.algos.vbase.backend.service.FileService;
import it.algos.vbase.backend.service.MongoService;
import it.algos.vbase.backend.wrapper.WrapLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static it.algos.vbase.backend.boot.BaseCost.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Tue, 16-Jan-2024
 * Time: 18:34
 */
@Service
public class LogService extends ModuloService<LogEntity> {

    @Autowired
    FileService fileService;

    @Autowired
    MongoService mongoService;


    /**
     * Costruttore invocato dalla sottoclasse concreta obbligatoriamente con due parametri <br>
     * Regola la entityClazz associata a questo Modulo <br>
     * Regola la viewClazz @Route associata a questo Modulo <br>
     */
    public LogService() {
        super(LogEntity.class, LogView.class);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public LogEntity newEntity() {
        return newEntity(null, VUOTA, null, null, VUOTA, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param utente      (obbligatorio)
     * @param modulo      (obbligatorio)
     * @param typeLog     (obbligatorio)
     * @param typeLevel   (obbligatorio)
     * @param modifica    (facoltativa)
     * @param descrizione (facoltativa)
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public LogEntity newEntity(UtenteEntity utente, String modulo, TypeLog typeLog, LogLevel typeLevel, String modifica, String descrizione) {
        LogEntity newEntityBean = LogEntity.builder()
                .utente(utente)
                .modulo(textService.isValid(modulo) ? modulo : null)
                .typeLog(typeLog == null ? TypeLog.system : typeLog)
                .typeLevel(typeLevel == null ? LogLevel.info : typeLevel)
                .evento(LocalDateTime.now())
                .modifica(textService.isValid(modifica) ? modifica : null)
                .descrizione(textService.isValid(descrizione) ? descrizione : null)
                .build();

        return newEntityBean;
    }

    public void crea(final WrapLog wrap) {
        LogEntity newBean;
        TypeLog typeLog = wrap.getType();
        LogLevel typeLevel = wrap.getLivello();
        String descrizione = wrap.getMessage();
        //        String companySigla = wrap.getCompanySigla();
        //        String userName = wrap.getUserName();
        //        String addressIP = wrap.getAddressIP();
        String modulo = wrap.getModulo();
        String classe = VUOTA;
        String metodo = VUOTA;
        String modifiche = wrap.getModifiche();
        int linea = 0;
        UtenteEntity utente = wrap.getUtente();

        if (wrap.getException() != null) {
            classe = wrap.getException().getClazz();
            classe = fileService.estraeClasseFinale(classe);
            metodo = wrap.getException().getMethod();
            linea = wrap.getException().getLineNum();
            //            modulo = wrap.getException().getLineNum();
        }

        //        entity.descrizione = textService.isValid(message) ? message : null;
        //        entity.company = textService.isValid(companySigla) ? companySigla : null;
        //        entity.user = textService.isValid(userName) ? userName : null;
        //        entity.address = textService.isValid(addressIP) ? addressIP : null;
        //        entity.classe = textService.isValid(classe) ? classe : null;
        //        entity.metodo = textService.isValid(metodo) ? metodo : null;
        //        entity.linea = linea;

        newBean = newEntity(utente, modulo, typeLog, typeLevel, modifiche, descrizione);
        mongoService.insert(newBean);
    }


    @Override
    public LogEntity afterInsert(LogEntity entityBean) {
        int appenderMax = APPENDER_MAX;
        int appenderOffset = APPENDER_OFFSET;
        long numEntities = count();
        List<LogEntity> listOrdinata = findAll();
        appenderMax = 70;
        appenderOffset = 5;

        if (numEntities > appenderMax) {
            for (LogEntity bean : listOrdinata.subList(0, appenderOffset)) {
                delete(bean);
            }
        }

        return entityBean;
    }

    public void deleteLogsScaduti() {
        for (Class entityClazz : reflectionService.getSubClazzEntity()) {
            if (annotationService.usaLogModifiche(entityClazz)) {
                deleteLogsScaduti(entityClazz);
            }
        }
    }


    public void deleteLogsScaduti(Class entityClazz) {
        String collectionName = mongoTemplate.getCollectionName(entityClazz);
        int giorniValiditaLogs = (int) annotationService.getGiorniValiditaLog(entityClazz).orElse(7);
        LocalDateTime primaDataValida = LocalDateTime.now().minusDays(giorniValiditaLogs);
        Query query = new Query();
        query.addCriteria(Criteria.where("modulo").is(collectionName));
        query.addCriteria(Criteria.where("evento").lt(primaDataValida));

        DeleteResult result = mongoService.mongoTemplate.remove(query, "logger");
        String message = String.format("cancellati %s record di %s ", result.getDeletedCount(), collectionName);
        logger.info(new WrapLog().message(message));
    }


}// end of CrudService class
