package com.hug.hug_api.domain.user.api;

import com.hug.hug_api.domain.user.dto.SignInRequestDto;
import com.hug.hug_api.domain.user.dto.SignUpDto;
import com.hug.hug_api.domain.user.dto.UserDto;
import com.hug.hug_api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpDto){
        return userService.signUp(signUpDto);
    }

    
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequestDto signInDto){
        return userService.signIn(signInDto);
    }

    @PutMapping("/user/nickname")
    public ResponseEntity<?> changeNickName(@RequestBody UserDto userDto){
        return userService.changeNickName(userDto);
    }


}
