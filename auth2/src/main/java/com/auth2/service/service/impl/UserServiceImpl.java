package com.auth2.service.service.impl;

import com.auth2.entity.SysUser;
import com.auth2.mapper.UserDao;
import com.auth2.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public SysUser loadUserByUsername(String username) {
        SysUser user = userDao.selectByUsername(username);
        return user;
    }
}
