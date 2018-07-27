package com.auth2.service;

import com.auth2.entity.MyUserDetails;
import com.auth2.service.service.UserService;
import com.config.oauth2config.constants.AuthoritiesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @description: 用户信息获取
 */
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * 通过 Username 加载用户详情
     *
     * @param username 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new MyUserDetails(userService.loadUserByUsername(username));
    }
}
