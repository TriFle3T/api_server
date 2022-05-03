package com.hug.hug_api.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error(e.getErrorCode().getDetail(),e.getErrorCode().getHttpStatus());
        return ErrorResponse.toResponseEntity(e);
    }
    @ExceptionHandler(value = {HttpClientErrorException.BadRequest.class, HttpMessageNotReadableException.class})
    protected ResponseEntity<ErrorResponse> handleBadException(Exception e){
        var ce = CustomException.builder().errorCode(ErrorCode.INVALID_ACCESS).build();
        log.error(ce.getErrorCode().getDetail(),ce.getErrorCode().getHttpStatus());
        return ErrorResponse.toResponseEntity(ce);
    }


    @ExceptionHandler(value = {BadCredentialsException.class})
    protected ResponseEntity<ErrorResponse> handleAuthenticationException(Exception e){
        var ce = CustomException.builder().errorCode(ErrorCode.INVALID_AUTH).build();
        log.error(ce.getErrorCode().getDetail(),ce.getErrorCode().getHttpStatus());
        return ErrorResponse.toResponseEntity(ce);
    }


    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<ErrorResponse> handleInternalServerError(Exception e){
        var ce = CustomException.builder().errorCode(ErrorCode.SERVER_ERROR).build();
        log.error(ce.getErrorCode().getDetail(),ce.getErrorCode().getHttpStatus());
        return ErrorResponse.toResponseEntity(ce);
    }

}
