package com.hug.hug_api.domain.diary.dto;

import com.hug.hug_api.domain.result.TestResult;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
    private List<TestResult> result;
    private int emoji;

    private String createdAt;

}
