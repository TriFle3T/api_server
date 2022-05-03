package com.hug.hug_api.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_ACCESS(BAD_REQUEST, "잘못된 요청입니다"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */

    INVALID_AUTH(UNAUTHORIZED, "인증 에러"),

    /* 403 FORBIDDEN : 권한이 없는 사용자 */
    FORBIDDEN_ERROR(FORBIDDEN, "권한이 없는 토큰입니다"),


    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    USER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),

    /* 500 INTERNAL_SERVER_ERROR : 서버 내부 오류, 처리하지 못 한 예외 발생 */
    SERVER_ERROR(INTERNAL_SERVER_ERROR,"서버 내부 오류")
    ;


    private final HttpStatus httpStatus;
    private final String detail;

}
