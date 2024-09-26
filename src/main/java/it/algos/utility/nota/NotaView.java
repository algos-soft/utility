package it.algos.utility.nota;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;
import it.algos.vbase.backend.annotation.AView;
import it.algos.vbase.backend.constant.Gruppo;
import it.algos.vbase.ui.view.CrudView;
import it.algos.vbase.ui.view.MainLayout;

/**
 * Project base24
 * Created by Algos
 * User: gac
 * Date: gio, 02-nov-2023
 * Time: 09:14
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@Route(value = "nota", layout = MainLayout.class)
@AView(menuGroup = Gruppo.UTILITY, menuName = "Note", lumo = LumoIcon.BAR_CHART, vaadin = VaadinIcon.CHECK)
public class NotaView extends CrudView {

    NotaView(NotaService moduloCrudService) {
        super(moduloCrudService, NotaList.class, NotaForm.class);
    }


}// end of @Route CrudView class
