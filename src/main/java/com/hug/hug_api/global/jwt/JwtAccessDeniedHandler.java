package com.hug.hug_api.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hug.hug_api.global.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        var map = new HashMap<String,Object>();

        map.put("data",null);
        map.put("message","권한 에러");
        map.put("error", ErrorResponse.builder()
                .error(HttpStatus.FORBIDDEN.name())
                .code("FORBIDDEN")
                .build());
        map.put("result","fail");

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(objectMapper.writeValueAsString(map));

    }
}
