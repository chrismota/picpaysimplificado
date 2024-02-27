package com.picpaysimplificado.repositories;

import com.picpaysimplificado.domain.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AdminRepository extends JpaRepository<Admin, String> {
    UserDetails findByLogin(String login);
}
