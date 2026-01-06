package com.example.phishing_link_checker.phishing.application;

import com.example.phishing_link_checker.phishing.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

class PhishingDetectionServiceTest {

    private PhishingDetectionPort phishingDetectionPort;
    private UrlExtractor urlExtractor;
    private SubscriptionStatusProvider subscriptionStatusProvider;
    private PhishingDetectionService service;

    @BeforeEach
    void setUp() {
        phishingDetectionPort = mock(PhishingDetectionPort.class);
        urlExtractor = mock(UrlExtractor.class);
        subscriptionStatusProvider = mock(SubscriptionStatusProvider.class);

        service = new PhishingDetectionService(phishingDetectionPort, urlExtractor, subscriptionStatusProvider);
    }

    @Test
    void shouldReturnFalseIfSubscriptionInactive() {
        Sms sms = new Sms("123", "321", "Message with URL");

        when(subscriptionStatusProvider.isSubscriptionActive(sms.recipient()))
                .thenReturn(Mono.just(false));

        StepVerifier.create(service.shouldBeBlock(sms))
                .expectNext(false)
                .verifyComplete();

        verifyNoInteractions(urlExtractor, phishingDetectionPort);
    }

    @Test
    void shouldReturnFalseIfNoUrls() {
        Sms sms = new Sms("123", "321", "No URLs here");

        when(subscriptionStatusProvider.isSubscriptionActive(sms.recipient()))
                .thenReturn(Mono.just(true));
        when(urlExtractor.extract(sms.message())).thenReturn(List.of());

        StepVerifier.create(service.shouldBeBlock(sms))
                .expectNext(false)
                .verifyComplete();

        verify(urlExtractor).extract(sms.message());
        verifyNoInteractions(phishingDetectionPort);
    }

    @Test
    void shouldReturnFalseIfUrlDoesNotBlock() {
        Sms sms = new Sms("123", "321", "Check this URL");
        var url = "http://example.com";

        when(subscriptionStatusProvider.isSubscriptionActive(sms.recipient()))
                .thenReturn(Mono.just(true));
        when(urlExtractor.extract(sms.message())).thenReturn(List.of(url));
        when(phishingDetectionPort.checkUrl(url))
                .thenReturn(Mono.just(new PhishingScore(url,RiskLevel.LOW)));

        StepVerifier.create(service.shouldBeBlock(sms))
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void shouldReturnTrueIfUrlBlocks() {
        Sms sms = new Sms("123", "321","Check this URL");
        var url = "http://bad.com";

        when(subscriptionStatusProvider.isSubscriptionActive(sms.recipient()))
                .thenReturn(Mono.just(true));
        when(urlExtractor.extract(sms.message())).thenReturn(List.of(url));
        when(phishingDetectionPort.checkUrl(url))
                .thenReturn(Mono.just(new PhishingScore(url, RiskLevel.CRITICAL)));

        StepVerifier.create(service.shouldBeBlock(sms))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void shouldReturnTrueIfAnyUrlBlocks() {
        Sms sms = new Sms("123", "321", "Multiple URLs");
        var goodUrl = "http://ok.com";
        var badUrl = "http://bad.com";

        when(subscriptionStatusProvider.isSubscriptionActive(sms.recipient()))
                .thenReturn(Mono.just(true));
        when(urlExtractor.extract(sms.message()))
                .thenReturn(List.of(goodUrl, badUrl));
        when(phishingDetectionPort.checkUrl(goodUrl))
                .thenReturn(Mono.just(new PhishingScore(goodUrl, RiskLevel.LOW)));
        when(phishingDetectionPort.checkUrl(badUrl))
                .thenReturn(Mono.just(new PhishingScore(badUrl, RiskLevel.CRITICAL)));

        StepVerifier.create(service.shouldBeBlock(sms))
                .expectNext(true)
                .verifyComplete();
    }
}