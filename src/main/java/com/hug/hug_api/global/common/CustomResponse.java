package com.hug.hug_api.global.common;

import com.hug.hug_api.global.exception.CustomException;
import com.hug.hug_api.global.exception.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Getter
@Setter
public class CustomResponse {
    @Builder
    @Getter
    @Setter
    private static class Body{
        private String result;
        private String message;
        private Object data;
        private Object error;
    }

    public ResponseEntity<?> success(Object data, String message, HttpStatus status){

        Body body = Body.builder()
                .data(data)
                .message(message)
                .result("success")
                .error(null)
                .build();
        return ResponseEntity.status(status).body(body);
    }

    public ResponseEntity<?> fail(CustomException e){
        ErrorCode errorCode = e.getErrorCode();

        Body body = Body.builder()
                .data(null)
                .message(errorCode.getDetail())
                .result("fail")
                .error(ErrorResponse.builder()
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.name())
                        .build())
                .build();
        return ResponseEntity.status(errorCode.getHttpStatus()).body(body);

    }

    // message만 있는 성공 응답
    public ResponseEntity<?> success(String message){
        return success(null,message,HttpStatus.OK);
    }

    public ResponseEntity<?>success(Object data,String message){
        return success(data,message,HttpStatus.OK);
    }

}
