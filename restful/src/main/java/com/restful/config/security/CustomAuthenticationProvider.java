package com.restful.config.security;

import com.restful.exception.ImageCodeIllegalException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 自定义认证
 * 验证码
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
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();
        String imageCode = details.getImageCode();
        String session_imageCode = details.getSession_imageCode();
        long session_imageTime = details.getSession_imageTime();

        if (imageCode == null || session_imageCode == null) {
            throw new ImageCodeIllegalException("验证码错误");
        }
        if (!imageCode.toLowerCase().equals(session_imageCode.toLowerCase())) {
            throw new ImageCodeIllegalException("验证码错误");
        } else {
            long nowTime = System.currentTimeMillis();
            if ((nowTime - session_imageTime) / 1000 > 60) { //大于60s,超时
                throw new ImageCodeIllegalException("验证码已超时");
            }
        }
        return authentication; //如果后续要有验证密码的provider，这里需要直接返回null
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
