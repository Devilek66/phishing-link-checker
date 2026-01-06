package com.example.phishing_link_checker.subscription.infastructure.web;

import com.example.phishing_link_checker.subscription.application.SubscriptionService;
import com.example.phishing_link_checker.subscription.domain.PhoneNumber;
import com.example.phishing_link_checker.subscription.infastructure.web.dto.StatusResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(
            SubscriptionService subscriptionService
    ) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/{phoneNumber}/activate")
    public Mono<Void> activate(
            @PathVariable String phoneNumber
    ) {
        return subscriptionService.activate(
                new PhoneNumber(phoneNumber)
        );
    }

    @PostMapping("/{phoneNumber}/deactivate")
    public Mono<Void> deactivate(
            @PathVariable String phoneNumber
    ) {
        return subscriptionService.deactivate(
                new PhoneNumber(phoneNumber)
        );
    }

    @GetMapping("/{phoneNumber}")
    public Mono<StatusResponse> status(
            @PathVariable String phoneNumber
    ) {
        return subscriptionService
                .isActive(new PhoneNumber(phoneNumber))
                .map(subscription -> new StatusResponse(subscription.isActive()));
    }
}