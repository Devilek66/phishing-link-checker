package com.example.phishing_link_checker.phishing.infrastructure.web;

import com.example.phishing_link_checker.phishing.application.PhishingDetectionService;
import com.example.phishing_link_checker.phishing.domain.Sms;
import com.example.phishing_link_checker.phishing.infrastructure.web.dto.VerificationRequest;
import com.example.phishing_link_checker.phishing.infrastructure.web.dto.VerificationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/verification")
public class VerificationController {
    private final PhishingDetectionService phishingDetectionService;

    public VerificationController(PhishingDetectionService phishingDetectionService) {
        this.phishingDetectionService = phishingDetectionService;
    }

    @PostMapping
    public Mono<VerificationResponse> verify(
            @RequestBody VerificationRequest request
    ) {
        Sms sms = new Sms(
                request.sender(),
                request.recipient(),
                request.message()
        );

        return phishingDetectionService
                .shouldBeBlock(sms)
                .map(VerificationResponse::new)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
}
