package com.hug.hug_api.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error(e.getErrorCode().getDetail(),e.getErrorCode().getHttpStatus());
        return ErrorResponse.toResponseEntity(e);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ErrorResponse> handleInternalServerError(Exception e){
        var ce = CustomException.builder().errorCode(ErrorCode.SERVER_ERROR).build();
        log.error(ce.getErrorCode().getDetail(),ce.getErrorCode().getHttpStatus());
        return ErrorResponse.toResponseEntity(ce);
    }
}
