package com.hug.hug_api.domain.user.service;

import com.hug.hug_api.domain.user.dao.UserRepository;
import com.hug.hug_api.domain.user.domain.User;
import com.hug.hug_api.domain.user.dto.SignUpDto;
import com.hug.hug_api.global.common.CustomResponse;
import com.hug.hug_api.global.exception.CustomException;
import com.hug.hug_api.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomResponse customResponse;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException(email));
    }
    
    public ResponseEntity<?> signUp(SignUpDto signUpDto) {

        // 해당 이메일로 가입된 계정이 있는 경우
        if(userRepository.existsByEmail(signUpDto.getEmail())) throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);

        var authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        var user = User.builder()
                .nickname(signUpDto.getNickname())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .diaries(null)
                .enabled(true)
                .authorities(authorities)
                .result(null)
                .build();

        userRepository.save(user);

        return customResponse.success("회원가입 성공",HttpStatus.CREATED);
    }
}
