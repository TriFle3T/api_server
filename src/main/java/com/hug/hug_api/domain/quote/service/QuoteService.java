//package com.hug.hug_api.domain.quote;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class QuoteService {
//
//    private final QuoteRepository quoteRepository;
//
//    public List<QuoteDto> findAll() {
//        return quoteRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
//    }
//
//    private QuoteDto toDto(Quote q){
//        return QuoteDto.builder()
//                .quotes(q.getQuotes())
//                .index(q.getIndex())
//                .theme(q.getTheme())
//                .build();
//    }
//
//
//}
