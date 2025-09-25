package it.algos.utility.security;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import it.algos.vbase.annotation.IView;
import it.algos.vbase.components.SimpleHorizontalLayout;
import it.algos.vbase.components.SimpleVerticalLayout;
import it.algos.vbase.constant.Gruppo;
import it.algos.vbase.security.IAuthenticationService;
import it.algos.vbase.service.MongoService;
import it.algos.vbase.ui.view.AView;
import it.algos.vbase.ui.view.MainLayout;
import it.algos.vbase.ui.wrapper.ASpan;
import it.algos.vbase.utility.SpringContext;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vbase.security.Roles.ROLE_SUPERUSER;

/**
 * Custom authentication service used to authenticate the users against
 * a user database specific to the application.
 */
@Service
public class UtilityAuthenticationService implements IAuthenticationService {

    private MongoTemplate mongoTemplate;

    @Override
    public Authentication authenticate(String name, String password) {
        doInjections();

        // db access to be implemented...
        final List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority(ROLE_SUPERUSER));
        final UserDetails principal = new User(name, password, grantedAuths);

        return new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);

    }

    private void doInjections(){
        mongoTemplate = SpringContext.getBean(MongoTemplate.class);
    }

}
