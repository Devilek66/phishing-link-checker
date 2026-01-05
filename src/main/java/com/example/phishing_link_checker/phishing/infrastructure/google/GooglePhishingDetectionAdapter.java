package com.example.phishing_link_checker.phishing.infrastructure.google;

import com.example.phishing_link_checker.phishing.domain.PhishingDetectionPort;
import com.example.phishing_link_checker.phishing.domain.PhishingScore;
import com.example.phishing_link_checker.phishing.infrastructure.google.dto.EvaluateUriRequest;
import com.example.phishing_link_checker.phishing.infrastructure.google.dto.EvaluateUriResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Profile({"prod", "test"})
public class GooglePhishingDetectionAdapter implements PhishingDetectionPort {

    private final GoogleWebRiskClient googleWebRiskClient;

    public GooglePhishingDetectionAdapter(GoogleWebRiskClient googleWebRiskClient){
        this.googleWebRiskClient = googleWebRiskClient;
    }

    @Override
    public Mono<PhishingScore> checkUrl(String url) {
        return googleWebRiskClient.evaluateUri(EvaluateUriRequest.EvaluateSocialEngineering(url))
                .map(response -> EvaluateUriResponse.map(url, response));
    }
}
