package com.doj0.inventory_management_api.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_STAFF;

    @Override
    public  String getAuthority() {
        return name();
    }
}
