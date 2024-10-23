package it.algos.utility.role;

import it.algos.vbase.enumeration.RisultatoReset;
import it.algos.vbase.enumeration.RoleEnum;
import it.algos.vbase.logic.ModuloService;
import org.springframework.stereotype.*;

import static it.algos.vbase.boot.BaseCost.VUOTA;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: dom, 22-ott-2023
 * Time: 12:17
 */
@Service
public class RoleService extends ModuloService<RoleEntity> {

    /**
     * Regola la entityClazz associata a questo Modulo e la passa alla superclasse <br>
     * Regola la viewClazz @Route associata a questo Modulo e la passa alla superclasse <br>
     * Regola la listClazz associata a questo Modulo e la passa alla superclasse <br>
     */
    public RoleService() {
        super(RoleEntity.class, RoleView.class);
    }


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    @Override
    public RoleEntity newEntity() {
        return newEntity(0, VUOTA);
    }

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     *
     * @param ordine (opzionale, unico)
     * @param code   (obbligatorio, unico)
     *
     * @return la nuova entity appena creata (con keyID ma non salvata)
     */
    public RoleEntity newEntity(final int ordine, final String code) {
        RoleEntity newEntityBean = RoleEntity.builder()
                .ordine(ordine == 0 ? nextOrdine() : ordine)
                .code(textService.isValid(code) ? code : null)
                .build();

        return newEntityBean;
    }

    @Override
    public RisultatoReset reset() {
        RoleEntity newBean;

        for (RoleEnum roleEnum : RoleEnum.values()) {
            newBean = newEntity(roleEnum.ordinal() + 1, roleEnum.name());
            if (newBean != null) {
                mappaBeans.put(roleEnum.name(), newBean);
            }
        }

        mappaBeans.values().stream().forEach(bean -> insertSave(bean));
        return RisultatoReset.vuotoMaCostruito;
    }

}// end of CrudService class
