package com.blog.springbootdeveloper.domain;

import com.blog.springbootdeveloper.aop.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseEntity implements UserDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    @Id
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Builder
    public User(String email, String password, String auth) {
        this.email = email;
        this.password = password;
    }

    @Override // 권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override // 사용자의 고유 id 반환
    public String getUsername() {
        return email;
    }

    @Override // 사용자 패스워드
    public String getPassword() {
        return password;
    }

    @Override // 계정 만료여부
    public boolean isAccountNonExpired() {
        return true; //만료되지 않음
    }

    @Override // 계정 잠금여부
    public boolean isAccountNonLocked() {
        return true; // 잠기지 않음
    }

    @Override // 패스워드 만료 여부
    public boolean isCredentialsNonExpired() {
        return true; // 만료되지 않음
    }

    @Override // 계정 사용가능 여부
    public boolean isEnabled() {
        return true; // 사용가능
    }
}
