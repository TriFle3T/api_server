package com.hug.hug_api.domain.user.api;

import com.hug.hug_api.domain.user.dto.SignUpDto;
import com.hug.hug_api.domain.user.dto.UserDto;
import com.hug.hug_api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpDto){
        return userService.signUp(signUpDto);
    }




}
