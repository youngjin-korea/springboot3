package com.blog.springbootdeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/*
*   appication.yml의 jwt 속성값 issuer, secretKey 값을 가져오기 위한 객체
* */
@Setter
@Getter
@Component
@ConfigurationProperties("jwt") // application.yml의 jwt와 매핑
public class JwtProperties {
    private String issuer;
    private String secretKey;
}
