package com.iweb.controller.sessions;

import com.iweb.config.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

@Controller
public class SessionController {

    private Logger logger = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;

    @Autowired
    private OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/index")
    String index() {
        return "/index";
    }

    @GetMapping("/welcome")
    String welcome(HttpSession session) {

        return "/welcome";
    }

    @GetMapping("/login")
    String login(Model model, User user) {
        model.addAttribute("user", user);
        return "/login";
    }

    @PostMapping("/login")
    public void signIn(Model model, User user, String imageCode,HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(!validCode(imageCode, request)){
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            logger.info("验证码错误，返回首页");
            response.sendRedirect("/login");
            return;
        }

        HttpEntity httpEntity = buildRequestInfoMap(user);
        //获取 Token
        ResponseEntity<OAuth2AccessToken> oAuth2AccessToken = restTemplate.exchange(oAuth2ProtectedResourceDetails.getAccessTokenUri(), HttpMethod.POST, httpEntity, OAuth2AccessToken.class);

        if (!ObjectUtils.isEmpty(oAuth2AccessToken.getBody()) && !StringUtils.isEmpty(oAuth2AccessToken.getBody().getValue())) {
            System.out.println(oAuth2AccessToken.getBody().getValue());
            response.sendRedirect("/welcome" + "?access_token=" + oAuth2AccessToken.getBody().getValue());

//        } else {
//            response.sendRedirect("/login");
        }
    }

    private boolean validCode(String verifyCode, HttpServletRequest request) {
        String session_verifyTime = (String) request.getSession().getAttribute("session_imageTime");
        Long st = Long.parseLong(session_verifyTime);
        if (st - System.currentTimeMillis() > 60) {
            return false;
        }
        StringBuffer sessiom_code = (StringBuffer) request.getSession().getAttribute("session_imageCode");
        return verifyCode.equals(sessiom_code);
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
