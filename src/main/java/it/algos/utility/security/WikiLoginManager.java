package it.algos.utility.security;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.dom.Style;
import it.algos.vbase.enumeration.TypeColor;
import it.algos.vbase.security.LoginManager;
import it.algos.vbase.ui.wrapper.ASpan;

//import static it.algos.wam24.boot.WamCost.TEMP_USER;

/**
 * Component responsibilities
 * <ul>
 *     <li>display the current user's icon</ul>
 *     <li>allow the user to Log Out</ul>
 *     <li>access the user info and configuration</ul>
 * </ul>
 */
public class WikiLoginManager extends LoginManager {


    @Override
    protected void init() {
        setWidth("3rem");
        Icon image = VaadinIcon.USER.create();
        image.setColor(TypeColor.rosso.getHtml());

        add(image);
        getStyle().setCursor("pointer");

        ContextMenu menu = buildMenu();
        image.addClickListener((ComponentEventListener<ClickEvent<Icon>>) iconClickEvent -> {
            menu.setVisible(true);
        });
    }

    protected ContextMenu buildMenu() {
        ContextMenu menu = new ContextMenu();
        menu.setTarget(this);
        menu.setOpenOnClick(true);

        String username = "Gac";

        ASpan span = ASpan.text(username).size("0.9rem").bold();
        Div wrapper = new Div(span);
        wrapper.getStyle()
                .setDisplay(Style.Display.FLEX)
                .setJustifyContent(Style.JustifyContent.CENTER)
                .setAlignItems(Style.AlignItems.CENTER)
                .setWidth("100%")
                .setHeight("2rem");
        menu.add(wrapper);
        menu.add(new Hr());

        return menu;
    }


}
