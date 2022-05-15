package com.hug.hug_api.domain.quote.dao;

import com.hug.hug_api.domain.quote.domain.Quote;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuoteRepository extends MongoRepository<Quote,String> {

    Quote findByIndex(int index);

}
