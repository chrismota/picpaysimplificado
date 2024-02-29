package com.picpaysimplificado.controllers;

import com.picpaysimplificado.domain.admin.Admin;
import com.picpaysimplificado.dtos.AuthAdminDTO;
import com.picpaysimplificado.dtos.LoginResponseDTO;
import com.picpaysimplificado.dtos.RegisterDTO;
import com.picpaysimplificado.infra.security.TokenService;
import com.picpaysimplificado.repositories.AdminRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthAdminController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AdminRepository repository;
    @Autowired
    private TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthAdminDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Admin) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Admin newAdmin = new Admin(data.login(), encryptedPassword, data.role());

        this.repository.save(newAdmin);

        return ResponseEntity.ok().build();
    }
}
