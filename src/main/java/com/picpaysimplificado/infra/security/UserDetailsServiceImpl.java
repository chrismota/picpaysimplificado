package com.picpaysimplificado.infra.security;

import com.picpaysimplificado.domain.admin.Admin;
import com.picpaysimplificado.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        return new UserDetailsImpl(admin);
    }

}
