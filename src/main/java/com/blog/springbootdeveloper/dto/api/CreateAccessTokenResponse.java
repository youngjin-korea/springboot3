package com.blog.springbootdeveloper.dto.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAccessTokenResponse {
    private String accessToken;
}
