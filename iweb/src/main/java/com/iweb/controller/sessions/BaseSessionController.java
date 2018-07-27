package com.iweb.controller.sessions;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class BaseSessionController {

    UserDetails getCurrentUser(){
        return (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
