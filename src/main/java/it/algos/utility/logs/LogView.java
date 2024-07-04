package it.algos.utility.logs;

import com.vaadin.flow.router.Route;
import it.algos.vbase.backend.annotation.AView;
import it.algos.vbase.backend.constant.Gruppo;
import it.algos.vbase.ui.view.CrudView;
import it.algos.vbase.ui.view.MainLayout;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: Tue, 16-Jan-2024
 * Time: 18:34
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@Route(value = "logger", layout = MainLayout.class)
@AView(menuGroup = Gruppo.UTILITY, menuName = "Logs")
public class LogView extends CrudView {


    /**
     * Costruttore alternativo invocato dalla sottoclasse concreta se si usa anche una formClazz specifico <br>
     * Mantiene il riferimento al CrudService Service (singleton) di questo Modulo <br>
     * Mantiene il riferimento ad una listClazz (CrudList) per creare l'istanza prototype <br>
     * Mantiene il riferimento ad una formClazz (CrudForm) per creare l'istanza prototype <br>
     */
    LogView(LogService crudModulo) {
        super(crudModulo, LogList.class, LogForm.class);
    }


}// end of @Route CrudView class
