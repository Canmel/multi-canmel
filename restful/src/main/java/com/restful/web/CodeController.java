package com.restful.web;

import com.core.entity.HttpResult;
import com.core.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 *  二维码生成器
 * </p>
 *
 * @author  *
 *   ┏ ┓   ┏ ┓
 *  ┏┛ ┻━━━┛ ┻┓
 *  ┃         ┃
 *  ┃    ━    ┃
 *  ┃  ┳┛  ┗┳ ┃
 *  ┃         ┃
 *  ┃    ┻    ┃
 *  ┃         ┃
 *  ┗━━┓    ┏━┛
 *     ┃    ┃神兽保佑
 *     ┃    ┃代码无BUG！
 *     ┃    ┗━━━━━━━┓
 *     ┃            ┣┓
 *     ┃            ┏┛
 *     ┗┓┓┏━━━━━━┳┓┏┛
 *      ┃┫┫      ┃┫┫
 *      ┗┻┛      ┗┻┛
 * @since 2018-08-12
 */

@RestController
@RequestMapping("/code")
public class CodeController {

    private static final String BASE_CODE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // 创建一个随机数生成器类
    private Random random = new Random();

    @GetMapping("/verify")
    HttpResult getVerify(HttpServletRequest request) {
        StringBuffer verifyCode = getRandomCode();
        request.getSession().setAttribute("session_imageCode", verifyCode);
        request.getSession().setAttribute("session_imageTime", Long.toString(System.currentTimeMillis()));
        Map m = new HashMap<>();
        m.put("verify", verifyCode);
        return Result.OK(m);
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
