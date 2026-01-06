package com.example.phishing_link_checker.subscription.domain;

public record Subscription(
        PhoneNumber phoneNumber,
        SubscriptionStatus status
) {
    public boolean isActive() {
        return status == SubscriptionStatus.ACTIVE;
    }
}