package com.example.coronaboard.bootstrap;

import com.example.coronaboard.domain.security.Authority;
import com.example.coronaboard.domain.security.User;
import com.example.coronaboard.repository.security.AuthorityRepository;
import com.example.coronaboard.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
/*

        Authority admin = authorityRepository.save(Authority.builder().role("ADMIN").build());
        Authority endUser = authorityRepository.save(Authority.builder().role("DEV").build());
        Authority developer = authorityRepository.save(Authority.builder().role("CUSTOMER").build());


        userRepository.save(User.builder()
                .username("Mahmoud")
                .password(passwordEncoder.encode("Mahmoud"))
                .email(UUID.randomUUID().toString())
                .authorities(Stream.of(admin, endUser, developer).collect(Collectors.toSet()))
                .build());

        userRepository.save(User.builder()
                .username("Saja")
                .password(passwordEncoder.encode("Saja"))
                .email(UUID.randomUUID().toString())
                .authorities(Stream.of(developer).collect(Collectors.toSet()))
                .build());
*/

    }
}
