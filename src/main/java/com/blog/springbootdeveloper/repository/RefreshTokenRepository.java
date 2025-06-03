package com.blog.springbootdeveloper.repository;

import com.blog.springbootdeveloper.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserId();
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
