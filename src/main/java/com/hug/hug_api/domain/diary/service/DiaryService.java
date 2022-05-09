package com.hug.hug_api.domain.diary.service;

import com.hug.hug_api.domain.diary.dto.DiaryDto;
import com.hug.hug_api.global.common.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final CustomResponse customResponse;

    public ResponseEntity<?> analyzeDiary(DiaryDto diaryDto) {

        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:5000/test")
                .build()
                .encode().toUri();

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("title", diaryDto.getTitle());
        body.add("content", diaryDto.getContent());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        var responseType = new ParameterizedTypeReference<DiaryDto>(){};

        var responseEntity = new RestTemplate().exchange(
                uri, HttpMethod.POST,entity,responseType
        );

        return customResponse.success(responseEntity.getBody(),"분석 성공");


    }
}
