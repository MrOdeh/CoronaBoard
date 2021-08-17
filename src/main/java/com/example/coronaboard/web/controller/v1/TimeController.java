package com.example.coronaboard.web.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequestMapping("/corona/v1/time")
@RestController
public class TimeController {

    @GetMapping(value = "/")
    public String currentTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
