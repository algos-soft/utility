package it.algos.utility.icona;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.*;
import it.algos.vbase.annotation.IView;
import it.algos.vbase.menu.Gruppo;
import it.algos.vbase.security.enumeration.Roles;
import it.algos.vbase.ui.view.*;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.*;

/**
 * Project wam24
 * Created by Algos
 * User: gac
 * Date: mer, 12-mar-2025
 * Time: 10:31
 *
 * @Route chiamata dal menu generale o dalla barra del browser <br>
 */
@PageTitle("Icone")
@Route(value = "icona", layout = MainLayout.class)
@RolesAllowed({Roles.SUPERUSER})
@IView(menuGroup = Gruppo.UTILITY, menuName = "Icone", vaadin = VaadinIcon.PALETTE)
public class IconaView extends AView {


    public IconaView(@Autowired IconaService moduloService) {
        super(IconaEntity.class, moduloService, IconaList.class, IconaForm.class);
    }


}// end of @Route AView class
