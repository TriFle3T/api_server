package com.hug.hug_api.global.common;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {

    private final LocalDateTime timeStamp = LocalDateTime.now();
    private String code;
    private String error;


}
