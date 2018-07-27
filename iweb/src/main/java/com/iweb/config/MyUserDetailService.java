package com.iweb.config;

import com.iweb.entity.MyUserDetails;
import com.iweb.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user = new SysUser();
        return new MyUserDetails(user);
    }
}
