package com.hug.hug_api.domain.result.dto;

import com.hug.hug_api.domain.quote.dto.SingleQuoteDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Builder
@Setter
@Getter
public class MainScreenResultDto {
    private HashMap<Integer,Float> result;
    private int emoji;
    private List<SingleQuoteDto> quotes;

}
