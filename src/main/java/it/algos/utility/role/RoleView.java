package it.algos.utility.role;

import com.vaadin.flow.router.Route;
import it.algos.vbase.backend.annotation.AView;
import it.algos.vbase.backend.constant.Gruppo;
import it.algos.vbase.ui.view.CrudView;
import it.algos.vbase.ui.view.MainLayout;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: dom, 22-ott-2023
 * Time: 12:17
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@Route(value = "role", layout = MainLayout.class)
@AView(menuGroup = Gruppo.UTILITY, menuName = "Ruoli")
public class RoleView extends CrudView {

    /**
     * Costruttore invocato dalla sottoclasse concreta obbligatoriamente con (almeno) due parametri <br>
     * Mantiene il riferimento al CrudService (singleton) di questo Modulo, iniettato dalla sottoclasse concreta <br>
     * Mantiene il riferimento ad una listClazz (AList) per creare l'istanza prototype <br>
     */
    RoleView(RoleService moduloCrudService) {
        super(moduloCrudService, RoleList.class);
    }


}// end of @Route CrudView class
