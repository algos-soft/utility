package it.algos.utility.preferenza;

import static it.algos.vbase.backend.boot.BaseCost.*;
import it.algos.vbase.backend.enumeration.*;
import it.algos.vbase.backend.logic.*;
import it.algos.vbase.backend.pref.*;
import it.algos.vbase.backend.wrapper.*;
import org.bson.types.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.time.*;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: dom, 22-ott-2023
 * Time: 13:47
 */
@Service
public class PreferenzaService extends ModuloService {

    private String message;


    /**
     * Costruttore invocato dalla sottoclasse concreta obbligatoriamente con due parametri <br>
     * Regola la entityClazz associata a questo Modulo <br>
     * Regola la viewClazz @Route associata a questo Modulo <br>
     */
    public PreferenzaService() {
        super(PreferenzaEntity.class, PreferenzaView.class);
    }


//    /**
//     * Regola le property di una ModelClazz <br>
//     * Di default prende tutti i fields della ModelClazz specifica <br>
//     */
//    public List<String> getPropertyNames() {
//        //@todo RIMETTERE spostato in LIST
//        return Arrays.asList("code", "type", "iniziale", "corrente", "descrizione", "critical", "dinamica", "base24");
//    }


    public PreferenzaEntity findOneByCode(String codeValue) {
        return (PreferenzaEntity) super.findOneByProperty(FIELD_NAME_CODE, codeValue);
    }

    public PreferenzaEntity insertOrUpdateIfExists(final IPref pref) {
        return insertOrUpdateIfExists(newEntity(pref));
    }

    public PreferenzaEntity insertOrUpdateIfExists(PreferenzaEntity newBean) {
        PreferenzaEntity oldBean = findOneByCode(newBean.getCode());
        if (oldBean == null) {
            return (PreferenzaEntity) mongoService.insert(newBean);
        }

        ObjectId oldId = oldBean.getId();
        oldBean.setId(null);
        if (oldBean.equals(newBean)) {
            return newBean;
        }
        else {
            oldBean.setId(oldId);
            oldBean.setIniziale(newBean.getIniziale());
            oldBean.setDescrizione(newBean.getDescrizione()); ;
            return (PreferenzaEntity) mongoService.save(oldBean);
        }
    }


    public PreferenzaEntity newEntity() {
        return newEntity(VUOTA, null, null, VUOTA, false, false, true);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public PreferenzaEntity newEntity(IPref pref) {
        String keyCode = pref.getKeyCode();
        TypePref type = pref.getType();
        Object defaultValue = pref.getDefaultValue();
        String descrizione = pref.getDescrizione();
        boolean critical = pref.isCritical();
        boolean dinamica = pref.isDinamica();
        boolean base24 = pref.isBase();

        return newEntity(keyCode, type, defaultValue, descrizione, critical, dinamica, base24);
    }

    public PreferenzaEntity newEntity(final String code, final TypePref type, final Object iniziale) {
        return newEntity(code, type, iniziale, code, false, false, false);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param code        (obbligatorio, unico)
     * @param type        (obbligatorio)
     * @param iniziale    (obbligatorio)
     * @param descrizione (facoltativo)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public PreferenzaEntity newEntity(String code, TypePref type, Object iniziale, String descrizione, boolean critical, boolean dinamica, boolean base24) {
        PreferenzaEntity newEntityBean = PreferenzaEntity.builder()
                .code(textService.isValid(code) ? code : null)
                .type(type)
                .iniziale(type != null ? type.objectToBytes(iniziale) : null)
                .corrente(type != null ? type.objectToBytes(iniziale) : null)
                .descrizione(textService.isValid(descrizione) ? descrizione : null)
                .critical(critical)
                .dinamica(dinamica)
                .base24(base24)
                .build();

        return newEntityBean;
    }

    @Override
    public Sort getBasicSort() {
        return Sort.by(Sort.Order.desc("base24"), Sort.Order.desc("critical"), Sort.Order.asc("code"));
    }


    public String getStr(IPref pref) {
        return getStr(pref.getType(), pref.getKeyCode());
    }

    public String getStr(TypePref type, String keyCode) {
        Object obj;

        if (type == TypePref.string) {
            obj = getValueCorrente(type, keyCode);
            if (obj instanceof String value) {
                return value;
            }
        }

        message = String.format("La preferenza [%s] di type (%s) è stata chiamata col metodo '%s' da usare solo coi type (%s)", keyCode, type, "getStr", TypePref.string.getTag());
        logger.warn(new WrapLog().message(message));
        return VUOTA;
    }


    public boolean is(IPref pref) {
        return is(pref.getType(), pref.getKeyCode());
    }

    public boolean is(TypePref type, String keyCode) {
        Object obj;

        if (type == TypePref.bool) {
            obj = getValueCorrente(type, keyCode);

            if (obj == null) {
                message = String.format("Nel database non esiste la preferenza [%s]. Controlla che l'enumeration delle preferenze sia stata caricata", keyCode);
                logger.error(new WrapLog().message(message));
                return false;
            }

            if (obj instanceof Boolean value) {
                return value;
            }
        }

        message = String.format("La preferenza [%s] di type (%s) è stata chiamata col metodo '%s' da usare solo coi type (%s)", keyCode, type, "is", TypePref.bool.getTag());
        logger.warn(new WrapLog().message(message));
        return false;
    }

    public int getInt(IPref pref) {
        return getInt(pref.getType(), pref.getKeyCode());
    }

    public int getInt(TypePref type, String keyCode) {
        Object obj;

        if (type == TypePref.integer) {
            obj = getValueCorrente(type, keyCode);
            if (obj instanceof Integer value) {
                return value;
            }
        }

        message = String.format("La preferenza [%s] di type (%s) è stata chiamata col metodo '%s' da usare solo coi type (%s)", keyCode, type, "getInt", TypePref.integer.getTag());
        logger.warn(new WrapLog().message(message));
        return 0;
    }


    public LocalDateTime getDateTime(IPref pref) {
        return getDateTime(pref.getType(), pref.getKeyCode());
    }

    public LocalDateTime getDateTime(TypePref type, String keyCode) {
        Object obj;

        if (type == TypePref.localdatetime) {
            obj = getValueCorrente(type, keyCode);
            if (obj instanceof LocalDateTime value) {
                return value;
            }
        }

        message = String.format("La preferenza [%s] di type (%s) è stata chiamata col metodo '%s' da usare solo coi type (%s)", keyCode, type, "getDateTime", TypePref.localdatetime.getTag());
        logger.warn(new WrapLog().message(message));
        return ERROR_DATA_TIME;
    }


    public void setValueCorrente(TypePref type, String keyCode, Object javaValue) {
        PreferenzaEntity preferenza = mongoService.findOneByProperty(PreferenzaEntity.class, FIELD_NAME_CODE, keyCode);

        if (preferenza == null) {
            return;
        }

        preferenza.setCorrente(type.objectToBytes(javaValue));
        mongoService.save(preferenza);
    }


    public Object getValueCorrente(TypePref type, String keyCode) {
        Object javaValue;
        PreferenzaEntity preferenza = (PreferenzaEntity) findOneByProperty(FIELD_NAME_CODE, keyCode);
        javaValue = preferenza != null ? type.bytesToObject(preferenza.getCorrente()) : null;

        return javaValue;
    }

    public byte[] get(String keyCode) {
        PreferenzaEntity preferenza = (PreferenzaEntity) findOneByProperty(FIELD_NAME_CODE, keyCode);

        return preferenza != null && preferenza.getType() == TypePref.unknown ? preferenza.getCorrente() : null;
    }

    public void put(String keyCode, byte[] bytes) {
        PreferenzaEntity preferenza = (PreferenzaEntity) findOneByProperty(FIELD_NAME_CODE, keyCode);

        if (preferenza != null && preferenza.getType() == TypePref.unknown) {
            preferenza.setCorrente(bytes);
            mongoService.save(preferenza);
        }
    }


    public Object getValue(String keyCode) {
        Object javaValue = null;
        TypePref type;

        PreferenzaEntity preferenza = (PreferenzaEntity) findOneByProperty(FIELD_NAME_CODE, keyCode);
        if (preferenza != null) {
            type = preferenza.getType();
            javaValue = type.bytesToObject(preferenza.getCorrente());
        }

        return javaValue;
    }

    public boolean saveDifferences(IPref enumPref) {
        boolean status = false;
        PreferenzaEntity existingEntityBean;
        String keyCode = enumPref.getKeyCode();

        existingEntityBean = (PreferenzaEntity) findOneByProperty(FIELD_NAME_CODE, keyCode);
        if (!existingEntityBean.getCode().equals(enumPref.getKeyCode()) || !existingEntityBean.getDescrizione().equals(enumPref.getDescrizione())) {
            existingEntityBean.setCode(enumPref.getKeyCode());
            existingEntityBean.setDescrizione(enumPref.getDescrizione());
            save(existingEntityBean);
        }

        return status;
    }

    //--serve solo ad 'oscurare' il metodo sovrascritto
    //--le funzionalità inerenti sono eseguite da download
    public RisultatoReset reset() {
        return null;
    }

    public RisultatoReset resetDelete() {
        return null;
    }

}
