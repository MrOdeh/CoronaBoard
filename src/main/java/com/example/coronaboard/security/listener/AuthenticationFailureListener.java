package com.example.coronaboard.security.listener;

import com.example.coronaboard.domain.security.LoginFailure;
import com.example.coronaboard.domain.security.User;
import com.example.coronaboard.repository.security.LoginFailureRepository;
import com.example.coronaboard.repository.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Log4j2
@Component
@RequiredArgsConstructor
public class AuthenticationFailureListener {

    private final LoginFailureRepository loginFailureRepository;
    private final UserRepository userRepository;

    @EventListener
    public void listen(AuthenticationFailureBadCredentialsEvent event) {
        log.info("Login failure");

        if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();
            LoginFailure.LoginFailureBuilder builder = LoginFailure.builder();

            if (token.getPrincipal() instanceof String) {
                log.info("Attempted Username: " + token.getPrincipal());
                builder.username((String) token.getPrincipal());
            }

            if (token.getDetails() instanceof WebAuthenticationDetails) {
                WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();

                log.info("Source IP: " + details.getRemoteAddress());
                builder.sourceIp(details.getRemoteAddress());
            }

            Optional<User> user = userRepository.findByEmail((String) token.getPrincipal());

            if(user.isPresent()){
                User userInfo = user.get();
                builder.userId(userInfo.getId());
                builder.userEmail(userInfo.getEmail());
            }

            LoginFailure failure = loginFailureRepository.save(builder.build());
            log.info("Failure Event: " + failure.getId());


            if (failure.getUserId() != null) {
                lockUserAccount(failure.getUserId());
            }
        }


    }
    private void lockUserAccount(String userId) {
        List<LoginFailure> failures = loginFailureRepository.findAllByUserIdAndCreatedDateIsAfter(userId,
                LocalDateTime.now().minusDays(1));

        log.info("size is # " + failures.size());
        if(failures.size() > 3){
            log.debug("Locking User Account... ");
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent()){
                User userInfo = user.get();
                userInfo.setAccountNonLocked(false);
                userRepository.save(userInfo);
            }
        }
    }
}
