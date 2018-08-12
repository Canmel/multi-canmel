package com.iauth2.config;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MyUserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    public MyUserDetails() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
