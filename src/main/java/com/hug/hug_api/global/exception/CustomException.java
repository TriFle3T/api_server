package com.hug.hug_api.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;

}
