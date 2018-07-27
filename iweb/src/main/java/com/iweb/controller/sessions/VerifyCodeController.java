package com.iweb.controller.sessions;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@RestController
public class VerifyCodeController {

    private static final String BASE_CODE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // 创建一个随机数生成器类
    private Random random = new Random();

    @GetMapping("/getVerify")
    StringBuffer getVerify(HttpServletRequest request) {
        StringBuffer verifyCode = getRandomCode();
        request.getSession().setAttribute("session_imageCode", verifyCode);
        request.getSession().setAttribute("session_imageTime", Long.toString(System.currentTimeMillis()));
        return verifyCode;
    }

    public StringBuffer getRandomCode() {
        int length = 4;
        StringBuffer randomCode = new StringBuffer();
        int size = BASE_CODE.length();
        for (int i = 0; i < length; i++) {
            int start = random.nextInt(size);
            String strRand = BASE_CODE.substring(start, start + 1);
            randomCode.append(strRand);
        }
        return randomCode;
    }
}
