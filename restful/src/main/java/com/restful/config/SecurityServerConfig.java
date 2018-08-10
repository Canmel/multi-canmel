package com.restful.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class SecurityServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login", "/index", "/", "/getVerify", "/css/**", "/javascript/**", "/images/**", "/plugins/**", "/error/**", "/fonts/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.headers().frameOptions().disable();
        http.csrf().disable().exceptionHandling()
                .authenticationEntryPoint(new Http401AuthenticationEntryPoint("Bearer realm=\"webrealm\""));
    }
}
