package com.example.coronaboard.domain.security;

import com.example.coronaboard.domain.audit.Auditable;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Document(collection = "loginfailure")
public class LoginFailure extends Auditable<String> {

    @Id
    private String id;

    private String username;

    private String userId;

    private String userEmail;

    private String sourceIp;
}
