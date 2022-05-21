package com.hug.hug_api.domain.user.service;

import com.hug.hug_api.domain.diary.dto.DiaryDto;
import com.hug.hug_api.domain.result.MainScreenResult;
import com.hug.hug_api.domain.user.dao.UserRepository;
import com.hug.hug_api.domain.user.domain.User;
import com.hug.hug_api.domain.user.dto.SignInRequestDto;
import com.hug.hug_api.domain.user.dto.SignInResponseDto;
import com.hug.hug_api.global.common.CustomResponse;
import com.hug.hug_api.global.exception.CustomException;
import com.hug.hug_api.global.exception.ErrorCode;
import com.hug.hug_api.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomResponse customResponse;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTemplate<String, String> redisTemplate;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public ResponseEntity<?> signIn(SignInRequestDto signInDto) {




        if(!userRepository.existsByEmail(signInDto.getEmail())) {
            var authorities = new HashSet<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            var newUser = User.builder()
                    .name(signInDto.getName())
                    .email(signInDto.getEmail())
                    .diaries(new ArrayList<>())
                    .enabled(true)
                    .authorities(authorities)
                    .result(new ArrayList<>())
                    .build();
            userRepository.save(newUser);
        }

        var user = userRepository.findByEmail(signInDto.getEmail()).get();

        var token = JwtTokenProvider.generateToken(user);

        var responseDto = SignInResponseDto.builder()
                .name(user.getName())
                .token(token)
                .result(user.getResult())
                .build();

        redisTemplate.opsForValue()
                .set(user.getEmail(), token,JwtTokenProvider.ACCESS_TIME
                        , TimeUnit.SECONDS);

        return customResponse.success(responseDto,"로그인 성공");
    }

}
