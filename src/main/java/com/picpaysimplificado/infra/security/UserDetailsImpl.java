package com.picpaysimplificado.infra.security;

import com.picpaysimplificado.domain.admin.Admin;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class UserDetailsImpl implements UserDetails {

    private Admin admin;

    public UserDetailsImpl(Admin admin) {
        this.admin = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return admin.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    } // Retorna a credencial do usuário que criamos anteriormente

    @Override
    public String getUsername() {
        return admin.getUsername();
    } // Retorna o nome de usuário do usuário que criamos anteriormente

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
