package it.algos.utility.wamicona;

import com.vaadin.flow.router.*;
import it.algos.vbase.annotation.IView;
import it.algos.vbase.constant.Gruppo;
import it.algos.vbase.ui.view.*;
import it.algos.wam24.view.WamMainLayout;
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
@Route(value = "icona", layout = WamMainLayout.class)
@IView(menuGroup = Gruppo.NESSUNO)
public class IconaView extends AView {


    public IconaView(@Autowired IconaService moduloService) {
        super(IconaEntity.class, moduloService, IconaList.class, IconaForm.class);
    }


}// end of @Route AView class
