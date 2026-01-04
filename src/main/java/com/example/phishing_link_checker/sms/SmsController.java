package com.example.phishing_link_checker.sms;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/sms")
public class SmsController {

    @GetMapping()
    public Mono<String> test()
    {
        return Mono.just("test data");
    }
}
