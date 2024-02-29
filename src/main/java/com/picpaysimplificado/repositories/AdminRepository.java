package com.picpaysimplificado.repositories;

import com.picpaysimplificado.domain.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    UserDetails findByLogin(String login);
}
