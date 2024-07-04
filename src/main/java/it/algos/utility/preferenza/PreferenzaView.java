package it.algos.utility.preferenza;

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
 * Time: 13:47
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@Route(value = "preferenza", layout = MainLayout.class)
@AView( menuGroup = Gruppo.UTILITY, menuName = "Preferenze")
public class PreferenzaView extends CrudView {


    /**
     * Costruttore alternativo invocato dalla sottoclasse concreta se si usa anche una formClazz specifico <br>
     * Mantiene il riferimento al CrudService Service (singleton) di questo Modulo <br>
     * Mantiene il riferimento ad una listClazz (CrudList) per creare l'istanza prototype <br>
     * Mantiene il riferimento ad una formClazz (CrudForm) per creare l'istanza prototype <br>
     */
    PreferenzaView(PreferenzaService moduloService) {
        super(moduloService, PreferenzaList.class, PreferenzaForm.class);
    }

}// end of @Route CrudView class
