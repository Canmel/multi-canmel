package com.iauth2.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

@Service
public class MyClientDetailsService implements ClientDetailsService {

    @Autowired
    private DataSource dataSource;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        // FIXME 这里每次请求都要运行四遍，不知道是为什么
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        return jdbcClientDetailsService.loadClientByClientId(clientId);
    }
}
