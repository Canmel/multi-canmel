package com.config.oauth2config.constants;

/**
 * @description: 权限常量
 */
public enum AuthoritiesEnum {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    ANONYMOUS("ROLE_ANONYMOUS");

    private String role;

    AuthoritiesEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
