package com.hug.hug_api.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hug.hug_api.domain.user.dao.UserRepository;
import com.hug.hug_api.domain.user.domain.User;
import com.hug.hug_api.domain.user.dto.SignInResponseDto;
import com.hug.hug_api.domain.user.dto.UserDto;
import com.hug.hug_api.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String,String> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

        String email = (String)oAuth2User.getAttributes().get("email");
        String name = (String)oAuth2User.getAttributes().get("name");

        var optionalUser = userRepository.findByEmail(email);
        User user;

        if(optionalUser.isEmpty()){             // 최초 로그인이라면 회원가입 처리를 한다.
            var authorities = new HashSet<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            user = User.builder()
                    .nickname(name)
                    .email(email)
                    .diaries(null)
                    .enabled(true)
                    .authorities(authorities)
                    .result(null)
                    .build();

            userRepository.save(user);
            log.info("create new user -> {}",email);

        }
        else{
            user = optionalUser.get();
        }

        var token = JwtTokenProvider.generateToken(user);

        log.info("create new token for {}, {}",email,token);

        redisTemplate.opsForValue()
                .set(email, token,JwtTokenProvider.ACCESS_TIME
                        , TimeUnit.SECONDS);


        writeTokenResponse(response, token);
    }
    private void writeTokenResponse(HttpServletResponse response, String token)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("auth", token);
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(token));
        writer.flush();
    }
}
