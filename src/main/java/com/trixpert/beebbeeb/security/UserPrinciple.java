package com.trixpert.beebbeeb.security;

import com.trixpert.beebbeeb.data.entites.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrinciple implements UserDetails {

    private final UserEntity user;
    private final List<GrantedAuthority> currentUserAuthorities = new ArrayList<>();


    public UserPrinciple(UserEntity user) {
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // all users have the same role - until now
        // this might be changed in the future by easy edit here
        user.getRoles().forEach(role -> currentUserAuthorities.add(new SimpleGrantedAuthority(role.getName())));

        return currentUserAuthorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        if (this.user.isPhoneFlag())
            return this.user.getPhone();
        else
            return this.user.getEmail();
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
        // don't have a password expiry policy so sue it as it's not
        // invalidated forever
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isActive();
    }
}
