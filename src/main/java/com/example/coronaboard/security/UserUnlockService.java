package com.example.coronaboard.security;

import com.example.coronaboard.domain.security.User;
import com.example.coronaboard.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class UserUnlockService {
    private final UserRepository userRepository;

    //@Scheduled(fixedRate = 5000)
    public void unlockAccounts(){
        log.debug("Running Unlock Accounts");

        List<User> lockedUsers = userRepository
                .findAllByAccountNonLockedAndLastModifiedDateIsBefore(false,
                        LocalDateTime.now().minusSeconds(30));

        if(lockedUsers.size() > 0){
            log.debug("Locked Accounts Found, Unlocking");
            lockedUsers.forEach(user -> user.setAccountNonLocked(true));

            userRepository.saveAll(lockedUsers);
        }
    }
}
