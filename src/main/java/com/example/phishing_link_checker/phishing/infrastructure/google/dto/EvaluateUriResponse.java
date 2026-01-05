package com.example.phishing_link_checker.phishing.infrastructure.google.dto;

import com.example.phishing_link_checker.phishing.domain.PhishingScore;
import com.example.phishing_link_checker.phishing.domain.RiskLevel;

import java.util.List;

public record EvaluateUriResponse(List<Score> scores) {
    public static PhishingScore map(String url, EvaluateUriResponse response)
    {
        return response.scores().stream()
                .filter(score -> score.threatType().equals(ThreatType.SOCIAL_ENGINEERING))
                .map(score -> new PhishingScore(
                        url,
                        mapRiskLevel(score.confidenceLevel())
                ))
                .findFirst()
                .orElse(new PhishingScore(url, RiskLevel.LOW));
    }

    private static RiskLevel mapRiskLevel(ConfidenceLevel level) {
        return switch (level) {
            case SAFE -> RiskLevel.NONE;
            case LOW -> RiskLevel.LOW;
            case MEDIUM -> RiskLevel.MEDIUM;
            case HIGH -> RiskLevel.HIGH;
            case HIGHER, VERY_HIGH, EXTREMELY_HIGH -> RiskLevel.CRITICAL;
            default -> RiskLevel.LOW;
        };
    }
}
