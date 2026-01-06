package com.example.phishing_link_checker.phishing.infrastructure.detection.mock;

import com.example.phishing_link_checker.phishing.domain.PhishingDetectionPort;
import com.example.phishing_link_checker.phishing.domain.PhishingScore;
import com.example.phishing_link_checker.phishing.domain.RiskLevel;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Profile("dev")
public class MockPhishingDetectionAdapter implements PhishingDetectionPort {
    @Override
    public Mono<PhishingScore> checkUrl(String url) {
        return Mono.just(new PhishingScore(url, deterministicRiskLevel(url)));
    }

    private RiskLevel deterministicRiskLevel(String url) {
        int hash = Math.abs(url.hashCode() % 100);

        if (hash < 70) return RiskLevel.NONE;
        if (hash < 85) return RiskLevel.LOW;
        if (hash < 95) return RiskLevel.HIGH;
        return RiskLevel.CRITICAL;
    }
}
