package com.hug.hug_api.domain.user.api;

import com.hug.hug_api.domain.diary.dto.DiaryDto;
import com.hug.hug_api.domain.diary.service.DiaryService;
import com.hug.hug_api.domain.quote.service.QuoteService;
import com.hug.hug_api.domain.user.dto.SignInRequestDto;
import com.hug.hug_api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final DiaryService diaryService;
    private final QuoteService quoteService;

    
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequestDto signInDto){
        return userService.signIn(signInDto);
    }


    @PostMapping("/user/{email}/diary")
    @PreAuthorize("#email == authentication.principal")
    public ResponseEntity<?> analyzeDiary(@RequestBody DiaryDto diaryDto,
                                          @PathVariable(name="email")String email){
        return diaryService.analyzeDiary(diaryDto,email);
    }

    @DeleteMapping("/user/{email}/diary/{index}")
    @PreAuthorize("#email == authentication.principal")
    public ResponseEntity<?> deleteDiary(@PathVariable(name = "index") int index,
                                        @PathVariable(name="email")String email){
        return diaryService.deleteDiary(index,email);
    }

    @GetMapping("/user/{email}")
    @PreAuthorize("#email == authentication.principal")
    public ResponseEntity<?> getUser(@PathVariable(name="email")String email){
        return userService.getUser(email);
    }


    @GetMapping("/greeting")
    public String greeting(){
        return "hihi!!!!";
    }

    @GetMapping("/insert")
    public String insertDB(){
        quoteService.insertFile();
        return "Done";
    }
}
