package com.hug.hug_api.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class SignUpDto {

    private String email;
    private String nickname;
    private String password;

}
