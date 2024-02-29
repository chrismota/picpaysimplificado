package com.picpaysimplificado.dtos;

import com.picpaysimplificado.domain.user.ClientType;

import java.math.BigDecimal;

public record ClientDTO(String firstName, String lastName, String document, BigDecimal balance, String email, String password, ClientType clientType){
}
