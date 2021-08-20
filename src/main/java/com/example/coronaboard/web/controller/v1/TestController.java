package com.example.coronaboard.web.controller.v1;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RequestMapping("/corona/v1/test")
@RestController
public class TestController {

    @PreAuthorize("hasAnyAuthority('ADMIN', 'DEV')")
    @GetMapping("/uuid")
    public String returnCustom(){
        return UUID.randomUUID().toString();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'DEV')")
    @GetMapping("/dayofweek")
    public Integer returnDate(){
        return LocalDateTime.now().getDayOfWeek().getValue();
    }
}
