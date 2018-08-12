package com.restful.web;

import com.core.entity.ErrorResult;
import com.core.entity.HttpResult;
import com.core.entity.Result;
import com.restful.config.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Collections;

@RestController
public class MyRestController {

    private Logger logger = Logger.getLogger(MyRestController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;

    @Autowired
    private OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails;

    @PostMapping("/login")
    public HttpResult login(User user) {
        HttpEntity httpEntity = buildRequestInfoMap(user);
        ResponseEntity<OAuth2AccessToken> oAuth2AccessToken = null;
        try {
            oAuth2AccessToken = restTemplate.exchange(oAuth2ProtectedResourceDetails.getAccessTokenUri(), HttpMethod.POST, httpEntity, OAuth2AccessToken.class);
            if (ObjectUtils.isEmpty(oAuth2AccessToken)) {
                return ErrorResult.UNAUTHORIZED("登录失败");
            }
        } catch (Exception e) {
            logger.error(e);
            return ErrorResult.UNAUTHORIZED("登录失败, 请检查用户名密码");
        }
        return Result.OK(oAuth2AccessToken.getBody().getValue(), "登录成功");
    }

    private HttpEntity buildRequestInfoMap(User loginUser) {
        String clientAndSecret = oAuth2ClientProperties.getClientId() + ":" + oAuth2ClientProperties.getClientSecret();
        clientAndSecret = "Basic " + Base64.getEncoder().encodeToString(clientAndSecret.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", clientAndSecret);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("username", Collections.singletonList(loginUser.getUsername()));
        map.put("password", Collections.singletonList(loginUser.getPassword()));
        map.put("grant_type", Collections.singletonList(oAuth2ProtectedResourceDetails.getGrantType()));
        map.put("scope", oAuth2ProtectedResourceDetails.getScope());
        //HttpEntity
        return new HttpEntity(map, httpHeaders);
    }
}
