package com.iauth2.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MyClientDetailsService implements ClientDetailsService {

    @Autowired
    private DataSource dataSource;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        // FIXME 这里每次请求都要运行四遍，不知道是为什么
        this.jdbcTemplate.setDataSource(dataSource);
        Map<String, Object> map = this.jdbcTemplate.queryForMap("select client_id, client_secret, resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove from oauth_client_details where client_id = ?", clientId);
        MyClientDetails mc = new MyClientDetails();
        mc.setClientId(map.get("client_id").toString());
        mc.setClientSecret(map.get("client_secret").toString());
        List ll = new ArrayList<>();
        ll.add(map.get("resource_ids").toString());
        mc.setResourceIds(ll);
        ll.clear();
        ll.add(map.get("scope").toString());
        mc.setScope(ll);
        mc.setRefreshTokenValiditySeconds((Integer) map.get("refresh_token_validity"));
        mc.setAccessTokenValiditySeconds((Integer) map.get("access_token_validity"));
        return mc;
    }
}
