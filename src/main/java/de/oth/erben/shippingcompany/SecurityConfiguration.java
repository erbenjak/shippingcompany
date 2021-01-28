package de.oth.erben.shippingcompany;

import de.oth.erben.shippingcompany.backend.security.AuthProvider;
import de.oth.erben.shippingcompany.backend.security.roles.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,jsr250Enabled  = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthProvider authProvider;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    private static final String[] ALLOW_ACCESS_WITHOUT_AUTHENTICATION = {
            "/css/**",
            "/js/**",
            "/icons/**",
            "/images/**",
            "/index",
            "/login",
            "/",
            "/home",
            "/restapi/**",
            "/restapi/order/**",
            "/letter",
            "/order/letter",
            "/order/letter/confirmation/**",
            "/track/**",
            "/sign/up"
    };

    private static final String[] ALLOW_ACCESS_AS_CUSTOMER = {
            "/order/**",
            "/customer/**"
    };

    private static final String[] ALLOW_ACCESS_AS_EMPLOYEE = {
            "/employee/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(ALLOW_ACCESS_WITHOUT_AUTHENTICATION)
                .permitAll()
                .antMatchers(ALLOW_ACCESS_AS_EMPLOYEE)
                .hasRole("EMPLOYEE")
                .antMatchers(ALLOW_ACCESS_AS_CUSTOMER)
                .hasRole("CUSTOMER")
                .anyRequest().authenticated();
        http
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("remember-me")
                .permitAll()
                .and()
                .rememberMe();

        http.cors().and().csrf().ignoringAntMatchers("/restapi/order/**");
    }
}
