package com.example.coronaboard.security;

import com.example.coronaboard.domain.security.User;
import com.example.coronaboard.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Log4j2
@RequiredArgsConstructor
@Service
public class MongoUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(email);

        if(!user.isPresent()){
            log.warn("User# {} tried to access.", (email != null ? email : "") );
            throw new UsernameNotFoundException("Username# " + (email != null ? email : "") + "not found");
        }

        return user.get();
    }
}
