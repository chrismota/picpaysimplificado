package com.picpaysimplificado.domain.admin;

public enum AdminRole {
    MASTER("master"),
    COMMON("common");

    private String role;

    AdminRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
