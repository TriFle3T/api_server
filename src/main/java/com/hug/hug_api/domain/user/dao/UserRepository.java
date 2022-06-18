package com.hug.hug_api.domain.user.dao;

import com.hug.hug_api.domain.user.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
