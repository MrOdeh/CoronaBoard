package com.example.coronaboard.repository.security;

import com.example.coronaboard.domain.security.LoginSuccess;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginSuccessRepository extends MongoRepository<LoginSuccess, String> {
}
