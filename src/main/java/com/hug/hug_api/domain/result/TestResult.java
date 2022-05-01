package com.hug.hug_api.domain.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestResult {
    private Float happy;
    private Float angry;
    private Float disgust;
    private Float fear;
    private Float neutral;
    private Float sad;
    private Float surprise;
}
