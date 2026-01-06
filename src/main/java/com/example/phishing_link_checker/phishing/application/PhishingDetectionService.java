package com.example.phishing_link_checker.phishing.application;

import com.example.phishing_link_checker.phishing.domain.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PhishingDetectionService {
    private final PhishingDetectionPort phishingDetectionPort;
    private final UrlExtractor urlExtractor;
    private final SubscriptionStatusProvider subscriptionStatusProvider;

    public PhishingDetectionService(PhishingDetectionPort phishingDetectionPort, UrlExtractor urlExtractor, SubscriptionStatusProvider subscriptionStatusProvider) {
        this.phishingDetectionPort = phishingDetectionPort;
        this.urlExtractor = urlExtractor;
        this.subscriptionStatusProvider = subscriptionStatusProvider;
    }

    public Mono<Boolean> shouldBeBlock(Sms sms) {
        return subscriptionStatusProvider.isSubscriptionActive(sms.recipient())
                .flatMap(isSubscribed -> {
                    if (!isSubscribed) {
                        return Mono.just(false);
                    }

                    List<String> urls = urlExtractor.extract(sms.message());

                    if (urls.isEmpty()) {
                        return Mono.just(false);
                    }

                    return Flux.fromIterable(urls)
                            .flatMap(phishingDetectionPort::checkUrl)
                            .any(PhishingScore::shouldBlock);
                });
    }
}
