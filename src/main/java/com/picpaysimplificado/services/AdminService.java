package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.admin.Admin;
import com.picpaysimplificado.domain.role.Role;
import com.picpaysimplificado.dtos.CreateAdminDto;
import com.picpaysimplificado.dtos.LoginAdminDto;
import com.picpaysimplificado.dtos.RecoveryJwtTokenDto;
import com.picpaysimplificado.infra.security.SecurityConfiguration;
import com.picpaysimplificado.infra.security.TokenService;
import com.picpaysimplificado.infra.security.UserDetailsImpl;
import com.picpaysimplificado.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    public RecoveryJwtTokenDto authenticateAdmin(LoginAdminDto loginAdminDto) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginAdminDto.username(), loginAdminDto.password());
        var authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryJwtTokenDto(tokenService.generateToken(userDetails));
    }

    public void createAdmin(CreateAdminDto createAdminDto) throws Exception {
        Optional<Admin> admin = this.adminRepository.findByUsername(createAdminDto.username());

        if (admin.isEmpty()) {
            Admin newAdmin = Admin.builder()
                    .username(createAdminDto.username())
                    .password(securityConfiguration.passwordEncoder().encode(createAdminDto.password()))
                    .roles(List.of(Role.builder().name(createAdminDto.role()).build()))
                    .build();

            adminRepository.save(newAdmin);
        } else {
            throw new Exception("An error occurred.");
        }
    }
}