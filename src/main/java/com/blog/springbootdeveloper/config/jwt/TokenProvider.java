package com.blog.springbootdeveloper.config.jwt;

import com.blog.springbootdeveloper.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

/*
 *   토큰을 생성, 올바른 토큰인지 유효성 검사, 토큰에서 필요한 값 가져오는 클래스
 * */
@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    //1. jwt 토큰 생성 메서드
    private String makeToken(Date expiry, User user) {
        Date now = new Date();

        // 토큰 구성 헤더(header).내용(payload)-단위 claim.서명(signature)
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 typ : JWT .
                .setIssuer(jwtProperties.getIssuer())         // 내용 iss : 이슈발행자
                .setIssuedAt(now)                             // 내용 iat : 현재 시간
                .setExpiration(expiry)                        // 내용 exp : 폐기 시간
                .setSubject(user.getEmail())                  // 내용 sub : 유저의 이메일
                .claim("id", user.getId())                 // 클레임 id : 유저 ID .
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // 서명 : 해싱 알고리즘, 비밀값
                .compact();
    }

    //2. jwt 토큰 유효성 검증 메서드
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) // 비밀값으로 복호화
                    .parseClaimsJws(token);
            // 유효한 토큰
            return true;
        } catch (Exception e) {
            // 복호화 과정에서 에러가 나면 유효하지 않은 토큰
            return false;
        }
    }

    //3. 토큰 기반으로 인증정보를 담은 Authentication 객체를 반환
    public Authentication getAuthentication(String token) {
        Claims claims = this.getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities),
                token, authorities);
    }

    //4. 토큰기반으로 유저 id 가져오는 메서드
    public Long getUserId(String token) {
        Claims claims = this.getClaims(token);
        return claims.get("id", Long.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
