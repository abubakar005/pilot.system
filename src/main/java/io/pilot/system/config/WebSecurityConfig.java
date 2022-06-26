package io.pilot.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value( "${auth.user.name}" )
    private String USER_NAME;

    @Value( "${auth.user.pass}" )
    private String USER_PASS;

    @Value( "${auth.user.role}" )
    private String USER_ROLE;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encodedPassword = encoder.encode(USER_PASS);
        auth.inMemoryAuthentication()
                .withUser(USER_NAME).password(encodedPassword).roles(USER_ROLE);
    }

    @Override
    protected void configure(HttpSecurity http)throws Exception {

        http.authorizeRequests()
                .antMatchers("**/swagger-ui/**").permitAll()
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .httpBasic();

        http.csrf().disable();
    }
}
