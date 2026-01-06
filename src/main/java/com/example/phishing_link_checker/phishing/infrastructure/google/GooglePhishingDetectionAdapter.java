package com.example.phishing_link_checker.phishing.infrastructure.google;

import com.example.phishing_link_checker.phishing.domain.PhishingDetectionPort;
import com.example.phishing_link_checker.phishing.domain.PhishingScore;
import com.example.phishing_link_checker.phishing.infrastructure.google.dto.EvaluateUriRequest;
import com.example.phishing_link_checker.phishing.infrastructure.google.dto.EvaluateUriResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@Profile({"prod", "test"})
public class GooglePhishingDetectionAdapter implements PhishingDetectionPort {

    private final GoogleWebRiskClient googleWebRiskClient;

    public GooglePhishingDetectionAdapter(GoogleWebRiskClient googleWebRiskClient){
        this.googleWebRiskClient = googleWebRiskClient;
    }

    @Cacheable(
            cacheNames = "phishingScore"
    )
    @Override
    public Mono<PhishingScore> checkUrl(String url) {
        return googleWebRiskClient.evaluateUri(EvaluateUriRequest.EvaluateSocialEngineering(url))
                .map(response -> EvaluateUriResponse.map(url, response))
                .cache(value -> Duration.ofHours(10),
                        error -> Duration.ofSeconds(10),
                        () -> Duration.ZERO);
    }
}
