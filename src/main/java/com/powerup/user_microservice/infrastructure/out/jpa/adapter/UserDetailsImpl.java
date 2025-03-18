package com.powerup.user_microservice.infrastructure.out.jpa.adapter;

import com.powerup.user_microservice.infrastructure.out.jpa.entity.UserRolesEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final transient UserRolesEntity userRolesEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = userRolesEntity.getRole().getName().name();
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return userRolesEntity.getUser().getPassword();
    }

    @Override
    public String getUsername() {
        return userRolesEntity.getUser().getEmail();
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

    public long getUserId(){
        return userRolesEntity.getUser().getId();
    }

}
