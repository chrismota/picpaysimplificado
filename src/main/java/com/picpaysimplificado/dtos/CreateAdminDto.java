package com.picpaysimplificado.dtos;

import com.picpaysimplificado.domain.role.RoleName;

public record CreateAdminDto(String username, String password, RoleName role) {
}
