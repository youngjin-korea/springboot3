package com.blog.springbootdeveloper.dto.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddUserRequest {
    private String email;
    private String password;

    @Builder
    public AddUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
