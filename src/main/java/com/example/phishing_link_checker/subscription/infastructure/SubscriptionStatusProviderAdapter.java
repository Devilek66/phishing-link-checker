package com.example.phishing_link_checker.subscription.infastructure;

import com.example.phishing_link_checker.phishing.domain.SubscriptionStatusProvider;
import com.example.phishing_link_checker.subscription.application.SubscriptionService;
import com.example.phishing_link_checker.subscription.domain.PhoneNumber;
import com.example.phishing_link_checker.subscription.domain.Subscription;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SubscriptionStatusProviderAdapter implements SubscriptionStatusProvider {
    private final SubscriptionService subscriptionService;

    public SubscriptionStatusProviderAdapter(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    public Mono<Boolean> isSubscriptionActive(String phoneNumber) {
        return subscriptionService.isActive(new PhoneNumber(phoneNumber))
                .map(Subscription::isActive);
    }
}
