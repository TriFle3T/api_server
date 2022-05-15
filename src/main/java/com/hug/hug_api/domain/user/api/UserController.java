package com.hug.hug_api.domain.user.api;

import com.hug.hug_api.domain.diary.dto.DiaryDto;
import com.hug.hug_api.domain.diary.service.DiaryService;
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
    private final DiaryService diaryService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto signUpDto){
        return userService.signUp(signUpDto);
    }

    
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequestDto signInDto){
        return userService.signIn(signInDto);
    }


    @PostMapping("/user/diary")
    public ResponseEntity<?> analyzeDiary(@RequestBody DiaryDto diaryDto){
        return diaryService.analyzeDiary(diaryDto);
    }

    @DeleteMapping("/user/diary/{index}")
    public ResponseEntity<?> deleteDiary(@PathVariable(name = "index") int index){
        return diaryService.deleteDiary(index);
    }

}
