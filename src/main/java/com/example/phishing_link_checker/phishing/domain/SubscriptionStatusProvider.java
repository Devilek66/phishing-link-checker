package com.example.phishing_link_checker.phishing.domain;

import reactor.core.publisher.Mono;

public interface SubscriptionStatusProvider {
    Mono<Boolean> isSubscriptionActive(String phoneNumber);
}
