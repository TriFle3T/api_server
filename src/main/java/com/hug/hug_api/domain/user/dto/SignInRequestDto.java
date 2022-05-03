package com.hug.hug_api.domain.user.dto;

import lombok.Getter;

@Getter
public class SignInRequestDto {
    private String email;
    private String password;
}
