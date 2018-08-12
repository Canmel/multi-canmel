package com.iauth2.config;

import com.config.oauth2config.config.AuthServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import java.util.concurrent.TimeUnit;

@Configuration
public class MyAuthorizationServerConfigurerAdapter extends AuthServerConfig {
    /**
     * 调用父类构造函数，设置令牌失效日期等信息
     */
    public MyAuthorizationServerConfigurerAdapter() {
        super((int) TimeUnit.DAYS.toSeconds(1), 0, false, false);
    }


    /**
     * 注入一个获取客户端实例的服务
     * 通过客户端提供的客户端id，使用jdbcTemplate 找到存在数据库中的ClientDetails
     */
    @Autowired
    public MyClientDetailsService myClientDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(myClientDetailsService);
    }
}
