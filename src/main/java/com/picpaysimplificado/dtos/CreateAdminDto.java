package com.picpaysimplificado.dtos;

import com.picpaysimplificado.domain.role.RoleName;

public record CreateUserDto(String email, String password, RoleName role) {
}
