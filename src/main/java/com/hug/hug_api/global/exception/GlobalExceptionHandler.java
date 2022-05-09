package com.hug.hug_api.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        log.warn("{}", ex.getMessage());
        return ErrorResponse.toResponseEntity(ex);
    }
    @ExceptionHandler(value = {HttpClientErrorException.BadRequest.class, HttpMessageNotReadableException.class})
    protected ResponseEntity<ErrorResponse> handleBadException(Exception ex){
        var ce = CustomException.builder().errorCode(ErrorCode.INVALID_ACCESS).build();
        log.warn("{}", ex.getMessage());

        return ErrorResponse.toResponseEntity(ce);
    }


    @ExceptionHandler(value = {BadCredentialsException.class})
    protected ResponseEntity<ErrorResponse> handleAuthenticationException(Exception ex){
        var ce = CustomException.builder().errorCode(ErrorCode.INVALID_AUTH).build();
        log.warn("{}", ex.getMessage());
        return ErrorResponse.toResponseEntity(ce);
    }


    @ExceptionHandler(value = {HttpServerErrorException.InternalServerError.class})
    protected ResponseEntity<ErrorResponse> handleInternalServerError(Exception ex){
        var ce = CustomException.builder().errorCode(ErrorCode.SERVER_ERROR).build();
        log.warn("{}", ex.getMessage());

        return ErrorResponse.toResponseEntity(ce);
    }

}
