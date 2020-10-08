package com.ipredictworld.application.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ipredictworld.application.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {


    private String id;

    private String name;

    private String email;

    private boolean accountNonExpired, accountNonLocked, enabled, credentialsNonExpired;

    @JsonIgnore
    private String password;


    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String id, String name,String email, String password, Collection<? extends GrantedAuthority> authorities){
        this.id = id;
        this.name = name;
        this.email = email;
        this.authorities = authorities;
        this.accountNonLocked = true;
        this.accountNonExpired = true;
        this.enabled = true;
        this.credentialsNonExpired = true;
    }

    public UserPrincipal(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.accountNonLocked = user.isNonLocked();
        this.accountNonExpired = true;
        this.enabled = true;
        this.credentialsNonExpired = true;

        System.out.println("User Roles "+user.getRole());
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.getRole().toString());

        this.authorities = authorities;
        System.out.println("User authorities "+authorities);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
