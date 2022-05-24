package com.hug.hug_api.domain.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Setter
public class TestResult {
    private Float happy;
    private Float angry;
    private Float disgust;
    private Float fear;
    private Float sad;
    private Float neutral;
    private Float surprise;
    private int index;
    private String content;
    private String speaker;
}
