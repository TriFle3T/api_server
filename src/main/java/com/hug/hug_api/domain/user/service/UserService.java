package com.hug.hug_api.domain.user.service;

import com.hug.hug_api.domain.user.dao.UserRepository;
import com.hug.hug_api.domain.user.dto.SignUpDto;
import com.hug.hug_api.domain.user.dto.UserDto;
import com.hug.hug_api.global.exception.CustomException;
import com.hug.hug_api.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException(email));
    }

    public ResponseEntity<?> signUp(SignUpDto signUpDto) {
        if(true)
            throw new CustomException(ErrorCode.USER_NOT_FOUND);

        return ResponseEntity.ok(null);
    }
}
