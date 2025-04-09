package com.finace.management.repository;

import com.finace.management.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUserNameOrEmail(String username, String email);
}
