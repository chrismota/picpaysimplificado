package com.picpaysimplificado.dtos;

import com.picpaysimplificado.domain.admin.AdminRole;

public record RegisterDTO(String login, String password, AdminRole role){
}
