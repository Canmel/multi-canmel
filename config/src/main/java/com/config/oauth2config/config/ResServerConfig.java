package com.config.oauth2config.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @description: OAuth2 资源服务器配置类
 */
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public abstract class ResServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired(required = false)
    private RemoteTokenServices remoteTokenServices;

    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;

    @Bean
    @Qualifier("authorizationHeaderRequestMatcher")
    public RequestMatcher authorizationHeaderRequestMatcher() {
        return new RequestHeaderRequestMatcher("Authorization");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        if (StringUtils.isEmpty(oAuth2ClientProperties.getClientId())) {
            resources.resourceId(oAuth2ClientProperties.getClientId());
        }
        if (Objects.nonNull(remoteTokenServices)) {
            resources.tokenServices(remoteTokenServices);
        }
    }
}