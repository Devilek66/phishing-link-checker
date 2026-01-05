package com.example.phishing_link_checker.phishing.domain;

import reactor.core.publisher.Mono;

public interface PhishingDetectionPort {
    Mono<PhishingScore> checkUrl(String url);
}
