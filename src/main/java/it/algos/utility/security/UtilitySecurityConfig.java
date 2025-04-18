package it.algos.utility.security;

import it.algos.vbase.security.SecurityConfig;
import org.springframework.context.annotation.Configuration;

/**
 * La presenza di questa @Configuration abilita SpringSecurity
 */
@Configuration
public class UtilitySecurityConfig extends SecurityConfig {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }
}
