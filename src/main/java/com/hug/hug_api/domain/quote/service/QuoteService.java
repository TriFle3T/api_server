package com.hug.hug_api.domain.quote.service;

import com.hug.hug_api.domain.quote.dao.QuoteRepository;
import com.hug.hug_api.domain.quote.domain.Quote;
import com.hug.hug_api.domain.quote.dto.SingleQuoteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class QuoteService {

    private final QuoteRepository quoteRepository;

    private final static List<String> EMOTIONS = List.of("행복","분노","혐오","두려움","중립","슬픔");

    public void insertFile(){
        ClassPathResource resource = new ClassPathResource("quote_DB");

        List<Quote> quotes = new ArrayList<>();

        for(int i =0;i<6;i++){
            quotes.add(
                    Quote.builder()
                            .quotes(new ArrayList<>())
                            .theme(EMOTIONS.get(i))
                            .index(i)
                            .build()
            );
        }

        try {

            InputStream is = new BufferedInputStream(resource.getInputStream());

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is)
            );

            List<String> content = new ArrayList<>();
            String line;
            while((line = reader.readLine()) != null){
                content.add(line);
            }
            content.forEach(
                    q->{
                        var items = q.split("\t");
                        var quote = quotes.get(Integer.parseInt(items[0]));
                        quote.getQuotes().add(
                                SingleQuoteDto.builder()
                                        .content(items[1])
                                        .speaker(items.length> 2 ? items[2]:null)
                                        .build()
                        );
                    }
            );
            quoteRepository.saveAll(quotes);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("격언 DB migrate하는 과정 중 알 수 없는 exception 발생!!!!");
        }
    }



}
