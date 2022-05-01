package com.hug.hug_api.domain.quote;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuoteRepository extends MongoRepository<Quote,String> {
}
