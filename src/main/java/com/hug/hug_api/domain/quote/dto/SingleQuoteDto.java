package com.hug.hug_api.domain.quote.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SingleQuoteDto {
    private String content;
    private String speaker;
}
