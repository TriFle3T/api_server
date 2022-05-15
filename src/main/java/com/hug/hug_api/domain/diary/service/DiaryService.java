package com.hug.hug_api.domain.diary.service;

import com.hug.hug_api.domain.diary.dto.DiaryDto;
import com.hug.hug_api.domain.result.TestResult;
import com.hug.hug_api.domain.user.dao.UserRepository;
import com.hug.hug_api.global.common.CustomResponse;
import com.hug.hug_api.global.exception.CustomException;
import com.hug.hug_api.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DiaryService {

    private final UserRepository userRepository;
    private final CustomResponse customResponse;

    public ResponseEntity<?> analyzeDiary(DiaryDto diaryDto) {

        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:5000/analyze")
                .build()
                .encode().toUri();

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("title", diaryDto.getTitle());
        body.add("content", diaryDto.getContent());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        var responseType = new ParameterizedTypeReference<TestResult>(){};

        var responseEntity = new RestTemplate().exchange(
                uri, HttpMethod.POST,entity,responseType
        );


        String email = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()) throw new CustomException(ErrorCode.USER_NOT_FOUND);

        var user = optionalUser.get();

        TestResult result = responseEntity.getBody();
        if(result == null) throw new CustomException(ErrorCode.ML_SERVER_ERROR);

        if(diaryDto.getResult()==null) diaryDto.setResult(List.of(result));
        else diaryDto.getResult().add(result);

        diaryDto.setEmoji(result.getEmoji());

        int idx = user.getCounter()+1;
        diaryDto.setIndex(idx);
        user.setCounter(idx);

        diaryDto.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));

        if(user.getDiaries() == null) user.setDiaries(List.of(diaryDto));
        else user.getDiaries().add(diaryDto);

        userRepository.save(user);

        return customResponse.success(diaryDto,"분석 성공");

    }

    public ResponseEntity<?> deleteDiary(int index) {

        String email = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) throw new CustomException(ErrorCode.USER_NOT_FOUND);

        var user = optionalUser.get();

        List<DiaryDto> deleted = user.getDiaries().stream().filter(
                d -> d.getIndex() != index
        ).collect(Collectors.toList());

        user.setDiaries(deleted);

        userRepository.save(user);


        return customResponse.success("삭제 성공");

    }
}
