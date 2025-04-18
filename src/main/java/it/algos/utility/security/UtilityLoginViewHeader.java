package it.algos.utility.security;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H2;
import it.algos.vbase.security.LoginViewHeader;

@org.springframework.stereotype.Component
public class UtilityLoginViewHeader implements LoginViewHeader {

    @Override
    public Component getComponent() {
        return new H2("Utility");
    }

}
