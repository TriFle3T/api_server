package com.hug.hug_api.domain.diary.dto;

import com.hug.hug_api.domain.result.dto.TestResultDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@RequiredArgsConstructor
public class DiaryDto {

    private int index;
    private String title;
    private String content;
    private TestResultDto result;
    private int emoji;

    private String createdAt;

}
