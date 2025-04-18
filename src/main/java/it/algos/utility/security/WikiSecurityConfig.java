package it.algos.utility.security;

import it.algos.vbase.security.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * La presenza di questa @Configuration abilita SpringSecurity
 */
@Configuration
public class WikiSecurityConfig extends SecurityConfig {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }
}
