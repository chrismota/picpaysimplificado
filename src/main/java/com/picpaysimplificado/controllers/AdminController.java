package com.picpaysimplificado.controllers;

import com.picpaysimplificado.dtos.*;
import com.picpaysimplificado.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/auth/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateAdmin(@RequestBody LoginAdminDto loginAdminDto) {
        RecoveryJwtTokenDto token = adminService.authenticateAdmin(loginAdminDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Void> createAdmin(@RequestBody CreateAdminDto createAdminDto) throws Exception {
        adminService.createAdmin(createAdminDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/test/logged")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/customer")
    public ResponseEntity<String> getCustomerAuthenticationTest() {
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }

}