package com.example.coronaboard.repository.security;

import com.example.coronaboard.domain.security.LoginFailure;
import com.example.coronaboard.domain.security.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoginFailureRepository extends MongoRepository<LoginFailure, String> {

    //findAllByUserAndAndCreatedByIsAfter

    List<LoginFailure> findAllByUserIdAndCreatedDateIsAfter(String user, LocalDateTime localDateTime);
}
