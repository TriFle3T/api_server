package com.hug.hug_api.domain.user.dto;

import com.hug.hug_api.domain.diary.Diary;
import com.hug.hug_api.domain.result.MainScreenResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Builder
@Getter
public class SignInResponseDto {

    private String nickname;
    private List<MainScreenResult> result;

    private String token;

}
