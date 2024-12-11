package com.argusoft.authmodule.repositories;

import com.argusoft.authmodule.entities.PasswordSetupToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordSetupTokenRepository extends JpaRepository<PasswordSetupToken, Long> {
    Optional<PasswordSetupToken> findByToken(String token);
}
