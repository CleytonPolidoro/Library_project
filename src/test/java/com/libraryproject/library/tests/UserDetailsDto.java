package com.libraryproject.library.tests;

import com.libraryproject.library.entities.projections.UserDetailsProjection;
import org.springframework.security.core.parameters.P;

public class UserDetailsDto implements UserDetailsProjection {
    private String username;
    private String password;
    private Long roleId;
    private String authority;

    public UserDetailsDto() {
    }

    public UserDetailsDto(String username, String password, Long roleId, String authority) {
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.authority = authority;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Long getRoleId() {
        return roleId;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
