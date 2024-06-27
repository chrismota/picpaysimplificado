package com.picpaysimplificado.controllers;

import com.picpaysimplificado.dtos.*;
import com.picpaysimplificado.services.AuthAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("auth")
//@RequiredArgsConstructor
//public class AuthAdminController {
//    private final AuthAdminService authAdminService;
//
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthAdminDTO authAdminDTO) {
//        LoginResponseDTO token = authAdminService.authenticateUser(authAdminDTO);
//        return ResponseEntity.ok(token);
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) throws Exception {
//        authAdminService.createUser(data);
//        return ResponseEntity.ok(HttpStatus.CREATED);
//    }
//}


@RestController
@RequestMapping("/users")
public class AuthAdminController {

    @Autowired
    private AuthAdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto token = adminService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateAdminDto createAdminDto) {
        adminService.createUser(createAdminDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/customer")
    public ResponseEntity<String> getCustomerAuthenticationTest() {
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/test/administrator")
    public ResponseEntity<String> getAdminAuthenticationTest() {
        return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
    }

}