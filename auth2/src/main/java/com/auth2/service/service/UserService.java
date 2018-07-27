package com.auth2.service.service;

import com.auth2.entity.SysUser;

public interface UserService {
    SysUser loadUserByUsername(String username);
}
