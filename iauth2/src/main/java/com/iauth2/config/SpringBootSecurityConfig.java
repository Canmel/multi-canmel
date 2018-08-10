package com.iauth2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringBootSecurityConfig {
    @Value("${security.oauth2.client.client-id}")
    public String clientId;

    @Value("${security.oauth2.client.client-secret}")
    public String secret;

    @Value("${security.oauth2.client.refresh-token-validity-seconds}")
    public Integer refreshTokenValiditySeconds;

    @Value("${security.oauth2.client.access-token-validity-seconds}")
    public Integer accessTokenValiditySeconds;
}
