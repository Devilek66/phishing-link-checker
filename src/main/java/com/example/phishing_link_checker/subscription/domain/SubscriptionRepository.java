package com.example.phishing_link_checker.subscription.domain;

import reactor.core.publisher.Mono;

public interface SubscriptionRepository {
    Mono<Subscription> findByPhoneNumber(PhoneNumber phoneNumber);

    Mono<Void> save(Subscription subscription);
}
