package com.example.phishing_link_checker.subscription.application;

import com.example.phishing_link_checker.subscription.domain.PhoneNumber;
import com.example.phishing_link_checker.subscription.domain.Subscription;
import com.example.phishing_link_checker.subscription.domain.SubscriptionRepository;
import com.example.phishing_link_checker.subscription.domain.SubscriptionStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public Mono<Void> activate(PhoneNumber phoneNumber) {
        return subscriptionRepository.save(new Subscription(phoneNumber, SubscriptionStatus.ACTIVE));
    }

    public Mono<Void> deactivate(PhoneNumber phoneNumber) {
        return subscriptionRepository.save(new Subscription(phoneNumber, SubscriptionStatus.INACTIVE));
    }

    public Mono<Subscription> isActive(PhoneNumber phoneNumber) {
        return subscriptionRepository.findByPhoneNumber(phoneNumber);
    }
}
