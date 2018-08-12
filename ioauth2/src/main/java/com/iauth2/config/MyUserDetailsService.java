package com.iauth2.config;

import com.iauth2.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 自定义用户详情服务
 */
@Service
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    public UserDao userDao;

    /**
     * 重写根据用户名查找用户，这里查出的密码是加密后的密码，要使其匹配上需要在前端页面将其加密
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUserDetails userDetails = new MyUserDetails(userDao.selectByUsername(username));
        return userDetails;
    }
}
