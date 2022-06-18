package com.hug.hug_api.domain.quote.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class QuoteDto {

    private int index;
    private String theme;
    private List<SingleQuoteDto> quotes;

}
