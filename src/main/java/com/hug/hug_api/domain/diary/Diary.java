package com.hug.hug_api.domain.diary;

import com.hug.hug_api.domain.result.TestResult;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

public class Diary {

    private int index;
    private String title;
    private String content;
    private List<TestResult> result;
    private int emoji;

    @CreatedDate
    private LocalDateTime createdAt;

}
