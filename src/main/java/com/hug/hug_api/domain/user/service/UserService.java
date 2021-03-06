package com.hug.hug_api.domain.user.service;

import com.hug.hug_api.domain.diary.dto.DiaryDto;
import com.hug.hug_api.domain.quote.dao.QuoteRepository;
import com.hug.hug_api.domain.result.dto.MainScreenResultDto;
import com.hug.hug_api.domain.user.dao.UserRepository;
import com.hug.hug_api.domain.user.domain.User;
import com.hug.hug_api.domain.user.dto.SignInRequestDto;
import com.hug.hug_api.domain.user.dto.SignInResponseDto;
import com.hug.hug_api.domain.user.dto.SignOutRequestDto;
import com.hug.hug_api.global.common.CustomResponse;
import com.hug.hug_api.global.exception.CustomException;
import com.hug.hug_api.global.exception.ErrorCode;
import com.hug.hug_api.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final QuoteRepository quoteRepository;
    private final CustomResponse customResponse;
    private final RedisTemplate<String, String> redisTemplate;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) throw new UsernameNotFoundException(email);

        return optionalUser.get();
    }

    public ResponseEntity<?> signIn(SignInRequestDto signInDto) {

        if(!userRepository.existsByEmail(signInDto.getEmail())) {
            var authorities = new HashSet<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            var newUser = User.builder()
                    .counter(-1)
                    .name(signInDto.getName())
                    .email(signInDto.getEmail())
                    .diaries(new ArrayList<>())
                    .enabled(true)
                    .authorities(authorities)
                    .build();

            userRepository.save(newUser);
            log.info("NEW USER CREATED {} ",signInDto.getEmail());
        }

        var user = userRepository.findByEmail(signInDto.getEmail()).get();

        var token = JwtTokenProvider.generateToken(user);

        var responseDto = SignInResponseDto.builder()
                .token(token)
                .build();

        redisTemplate.opsForValue()
                .set(user.getEmail(), token,JwtTokenProvider.ACCESS_TIME
                        , TimeUnit.SECONDS);
        log.info("NEW TOKEN CREATED {}",token);

        return customResponse.success(responseDto,"????????? ??????");
    }


    public ResponseEntity<?> getUser(String email) {

        var optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) throw new CustomException(ErrorCode.USER_NOT_FOUND);

        var user = optionalUser.get();
        var diaries = user.getDiaries();
        if(diaries.isEmpty()) return customResponse.success("?????? ??????");

        MainScreenResultDto result = getMainScreenResult(diaries);

        return customResponse.success(result,"?????? ??????");

    }

    private MainScreenResultDto getMainScreenResult(List<DiaryDto> diaries) {
        // ?????? 1??? ???????????? ???
        var criteria = LocalDateTime.now().minusDays(30);

        var filtered = diaries.stream().filter(
                d->{
                    String[] date = d.getCreatedAt().split("-");
                    LocalDateTime cmp = LocalDateTime.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]),0,0);
                    return cmp.isAfter(criteria);
                }
        ).collect(Collectors.toList());

        if(filtered.isEmpty()) return null;

        // Map ??????
        var result = new HashMap<Integer, Float>();
        for(int i=0; i< 7;i++) result.put(i, 0f);

        filtered.forEach(
                f ->{
                    var key = f.getEmoji();
                    result.put(key,result.get(key)+1);
                }
        );

        var total = filtered.size();

        for(int i =0; i< 7;i++){
            result.put(i,result.get(i));
        }

        List<Integer> listKeySet = new ArrayList<>(result.keySet());

        // ????????????
        listKeySet.sort((value1, value2) -> (result.get(value2).compareTo(result.get(value1))));

        var ret = new HashMap<Integer,Float>();
        Integer key;
        Float sum=0f;

        for(int i=0;i<4;i++){
            key = listKeySet.get(i);
            sum += result.get(key);
            ret.put(key,(result.get(key))/total*100);
        }
        ret.put(7,(total-sum)/total*100);

        var emoji = listKeySet.get(0);
        var quotes = quoteRepository.findByIndex(emoji)
                .getQuotes();

        return MainScreenResultDto.builder()
                .emoji(listKeySet.get(0))
                .quotes(quotes)
                .result(ret)
                .build();
    }

    public ResponseEntity<?> signOut(String email, SignOutRequestDto signOutRequestDto) {

        var accessTokenInfo = JwtTokenProvider.verify(signOutRequestDto.getToken());

        String isLogout = redisTemplate.opsForValue().get(signOutRequestDto.getToken());

        if(!accessTokenInfo.isSuccess() || !ObjectUtils.isEmpty(isLogout)) throw new CustomException(ErrorCode.INVALID_ACCESS);

        // redis?????? refreshToken ?????????
        if(redisTemplate.opsForValue().get(email)!=null){
            redisTemplate.delete(email);
        }

        // redis black list??? ??????
        redisTemplate.opsForValue()
                .set(signOutRequestDto.getToken(), "logout", JwtTokenProvider.ACCESS_TIME, TimeUnit.SECONDS);

        log.info("LOGOUT {}",email);

        return customResponse.success("???????????? ??????");
    }
}
