package com.example.phishing_link_checker.subscription.infastructure;

import com.example.phishing_link_checker.subscription.domain.PhoneNumber;
import com.example.phishing_link_checker.subscription.domain.Subscription;
import com.example.phishing_link_checker.subscription.domain.SubscriptionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemorySubscriptionRepository implements SubscriptionRepository {
    private final Map<String, Subscription> store =
            new ConcurrentHashMap<>();

    @Override
    public Mono<Subscription> findByPhoneNumber(
            PhoneNumber phoneNumber
    ) {
        return Mono.justOrEmpty(
                store.get(phoneNumber.value())
        );
    }

    @Override
    public Mono<Void> save(Subscription subscription) {
        store.put(
                subscription.phoneNumber().value(),
                subscription
        );
        return Mono.empty();
    }
}
