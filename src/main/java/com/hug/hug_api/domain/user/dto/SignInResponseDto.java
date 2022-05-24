package com.hug.hug_api.domain.user.dto;

import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class SignInResponseDto {

    private String token;

}
