package com.hug.hug_api.domain.user.dto;

import com.hug.hug_api.domain.result.MainScreenResult;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SignInResponseDto {

    private String name;
    private List<MainScreenResult> result;

    private String token;

}
