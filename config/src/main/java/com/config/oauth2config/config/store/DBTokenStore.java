package com.config.oauth2config.config.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * @description: 使用数据库存取令牌
 */
public class DBTokenStore {

    @Autowired
    private DataSource dataSource;

    @Bean
    public TokenStore tokenStore(){
        return new JdbcTokenStore(dataSource);
    }

}
