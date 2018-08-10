package com.auth2.config;

import com.config.oauth2config.config.AuthServerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;

import java.util.concurrent.TimeUnit;

/**
 * @description: OAuth2 授权服务器配置
 */
@Configuration
public class AuthorizationServerConfig extends AuthServerConfig {

    /**
     * 调用父类构造函数，设置令牌失效日期等信息
     */
    public AuthorizationServerConfig() {
        super((int) TimeUnit.DAYS.toSeconds(1), 0, false, false);
    }

    /**
     * 配置客户端详情
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        super.configure(clients);
        clients.inMemory()                       // 使用内存存储客户端信息
                .withClient("spring-boot-web-client")       // client_id
                .secret("Q!W@E#R$T%Y^U&I*O(P)")                     // client_secret
                .authorizedGrantTypes("password", "refresh_token")  // 该client允许的授权类型
                .accessTokenValiditySeconds(3600)                   // Token 的有效期
                .scopes("read")                                     // 允许的授权范围
                .autoApprove(true);                                 // 登录后绕过批准询问(/oauth/confirm_access)
    }
}
