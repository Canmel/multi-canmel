package com.iweb.controller.sessions;

import com.iweb.config.User;
import com.iweb.entity.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

@RestController
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;

    @Autowired
    private OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails;

    @PostMapping("/login")
    public ResponseModel signIn(Model model, User user, String imageCode, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView mv = new ModelAndView();
        ResponseModel rm = new ResponseModel();
        if (!validCode(imageCode, request)) {
            logger.info("验证码错误或已过期");
            rm.setHttpStatus(HttpStatus.UNAUTHORIZED.value());
            rm.setMsg("验证码错误或已过期");
            return rm;
        }
        HttpEntity httpEntity = buildRequestInfoMap(user);
        //获取 Token
        ResponseEntity<OAuth2AccessToken> oAuth2AccessToken = restTemplate.exchange(oAuth2ProtectedResourceDetails.getAccessTokenUri(), HttpMethod.POST, httpEntity, OAuth2AccessToken.class);
        if (!ObjectUtils.isEmpty(oAuth2AccessToken.getBody()) && !StringUtils.isEmpty(oAuth2AccessToken.getBody().getValue())) {
            request.getSession().setAttribute("access_token", oAuth2AccessToken.getBody().getValue());
            rm.setAccess_token(oAuth2AccessToken.getBody().getValue());
            rm.setMsg("认证成功");
            rm.setHttpStatus(HttpStatus.OK.value());
            return rm;
        }
        rm.setHttpStatus(HttpStatus.UNAUTHORIZED.value());
        rm.setMsg("认证失败");
        return rm;
    }

    private boolean validCode(String verifyCode, HttpServletRequest request) {
        String session_verifyTime = (String) request.getSession().getAttribute("session_imageTime");
        Long st = Long.parseLong(session_verifyTime);
        if (st - System.currentTimeMillis() > 60) {
            return false;
        }
        StringBuffer sessiom_code = (StringBuffer) request.getSession().getAttribute("session_imageCode");
        return verifyCode.equals(sessiom_code.toString());
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
