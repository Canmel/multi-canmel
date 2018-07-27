package com.iweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class SecurityServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthenticationProvider customAuthenticationProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login", "/index", "/", "/getVerify", "/css/**", "/javascript/**", "/images/**", "/plugins/**", "/error/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.csrf().disable().exceptionHandling()
                .authenticationEntryPoint(new Http401AuthenticationEntryPoint("Bearer realm=\"webrealm\""));
    }


}
