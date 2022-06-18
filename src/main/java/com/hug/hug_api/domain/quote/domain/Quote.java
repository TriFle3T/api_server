package com.hug.hug_api.domain.quote.domain;

import com.hug.hug_api.domain.quote.dto.SingleQuoteDto;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "quote")
@Data
@Builder
public class Quote {

    @Id
    private String id;

    @Indexed(unique = true)
    private int index;

    private String theme;
    private List<SingleQuoteDto> quotes;

}
