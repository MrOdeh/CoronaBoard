package com.example.coronaboard.repository.security;

import com.example.coronaboard.domain.security.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
    Optional<User> findById(String username);

    //findAllByAccountNonLockedAndLastModifiedDateIsBefore
    List<User> findAllByAccountNonLockedAndLastModifiedDateIsBefore(boolean expression, LocalDateTime localDateTime);
}
