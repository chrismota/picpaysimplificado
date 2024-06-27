package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.admin.Admin;
import com.picpaysimplificado.domain.role.Role;
import com.picpaysimplificado.dtos.CreateAdminDto;
import com.picpaysimplificado.dtos.LoginUserDto;
import com.picpaysimplificado.dtos.RecoveryJwtTokenDto;
import com.picpaysimplificado.infra.security.SecurityConfiguration;
import com.picpaysimplificado.infra.security.TokenService;
import com.picpaysimplificado.infra.security.UserDetailsImpl;
import com.picpaysimplificado.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
//
//@Service
//public class AuthAdminService {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private TokenService tokenService;
//    @Autowired
//    AdminRepository adminRepository;
//    @Autowired
//    private SecurityConfiguration securityConfiguration;
//
//    public LoginResponseDTO authenticateUser(AuthAdminDTO authAdminDTO){
//
////        Optional<Admin> admin = this.adminRepository.findByLogin(authAdminDTO.login());
////
////        if (admin.isPresent()) {
////            var passwordMatches = securityConfiguration.passwordEncoder().matches(authAdminDTO.password(), admin.get().getPassword());
////            if (passwordMatches) {
//                var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authAdminDTO.login(), authAdminDTO.password());
//                var authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//
//                var token = tokenService.generateToken((UserDetailsImpl) authentication.getPrincipal());
//
////                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//
//                return new LoginResponseDTO(token);
////            }
////        }
////        throw new AuthenticationException("Error during login atempt");
//    }
//
//    public void createUser(RegisterDTO registerDTO) throws Exception {
//        Optional<Admin> admin = this.adminRepository.findByLogin(registerDTO.login());
//
//        if (admin.isEmpty()) {
//            Admin newAdmin = new Admin();
//            newAdmin.setPassword(securityConfiguration.passwordEncoder().encode(registerDTO.password()));
//            newAdmin.setLogin(registerDTO.login());
//            newAdmin.setRole(registerDTO.role());
//            this.adminRepository.save(newAdmin);
//        } else {
//            throw new Exception("An error occurred.");
//        }
//    }
//}


@Service
public class AuthAdminService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(tokenService.generateToken(userDetails));
    }
    public void createUser(CreateAdminDto createAdminDto) {

        Admin newAdmin = Admin.builder()
                .username(createAdminDto.username())
                .password(securityConfiguration.passwordEncoder().encode(createAdminDto.password()))
                // Atribui ao usuário uma permissão específica
                .roles(List.of(Role.builder().name(createAdminDto.role()).build()))
                .build();

        adminRepository.save(newAdmin);
    }
}