package com.hug.hug_api.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hug.hug_api.global.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException{

            response.setContentType("application/json;charset=utf-8");
            var map = new HashMap<String,Object>();

            map.put("data",null);
            map.put("message","인증 에러");
            map.put("error", ErrorResponse.builder()
                            .error(HttpStatus.UNAUTHORIZED.name())
                            .code("AUTH_ERROR")
                    .build());
            map.put("result","fail");

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(objectMapper.writeValueAsString(map));

        }

}
