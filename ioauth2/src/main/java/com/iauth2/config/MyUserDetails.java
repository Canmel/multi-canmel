package com.iauth2.config;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class MyUserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private User user;
    private List authorities;

    public MyUserDetails(User user) {
        this.user = user;
    }

    public MyUserDetails() {
    }

    public void setAuthorities(List authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
