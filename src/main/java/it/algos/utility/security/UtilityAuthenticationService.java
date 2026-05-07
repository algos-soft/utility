package it.algos.utility.security;

import it.algos.vbase.security.IAuthenticationService;
import it.algos.vbase.security.enumeration.Roles;
import it.algos.vbase.utility.SpringContext;
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
        grantedAuths.add(new SimpleGrantedAuthority(Roles.ROLE_SUPERUSER.name()));
        final UserDetails principal = new User(name, password, grantedAuths);

        return new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);

    }

    private void doInjections(){
        mongoTemplate = SpringContext.getBean(MongoTemplate.class);
    }

}
