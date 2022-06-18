package com.hug.hug_api.global.exception;

import com.hug.hug_api.global.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.net.UnknownHostException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final CustomResponse response;

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<?> handleCustomException(CustomException ex) {
        log.warn("{}", ex.getMessage());
        return response.fail(ex);
    }


    @ExceptionHandler(value = {UsernameNotFoundException.class})
    protected ResponseEntity<?> handleUsernameNotFoundException(Exception ex){
        var ce = CustomException.builder().errorCode(ErrorCode.USER_NOT_FOUND).build();
        log.warn("{}", ex.getMessage());
        return response.fail(ce);
    }


    @ExceptionHandler(value = {HttpClientErrorException.BadRequest.class, HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class})
    protected ResponseEntity<?> handleBadException(Exception ex){
        var ce = CustomException.builder().errorCode(ErrorCode.INVALID_ACCESS).build();
        log.warn("{}", ex.getMessage());
        return response.fail(ce);
    }

    @ExceptionHandler(value = UnknownHostException.class)
    protected ResponseEntity<?> handleUnKnownHostException(Exception ex){
        var ce = CustomException.builder().errorCode(ErrorCode.SERVER_ERROR).build();
        log.warn("{}", ex.getMessage());
        return response.fail(ce);
    }



    @ExceptionHandler(value = {BadCredentialsException.class})
    protected ResponseEntity<?> handleAuthenticationException(Exception ex){
        var ce = CustomException.builder().errorCode(ErrorCode.INVALID_AUTH).build();
        log.warn("{}", ex.getMessage());
        return response.fail(ce);
    }


    @ExceptionHandler(value = {HttpServerErrorException.InternalServerError.class})
    protected ResponseEntity<?> handleInternalServerError(Exception ex){
        var ce = CustomException.builder().errorCode(ErrorCode.SERVER_ERROR).build();
        log.warn("{}", ex.getMessage());
        return response.fail(ce);
    }

}
