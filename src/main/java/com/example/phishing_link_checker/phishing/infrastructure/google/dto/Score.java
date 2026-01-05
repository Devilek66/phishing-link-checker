package com.example.phishing_link_checker.phishing.infrastructure.google.dto;

public record Score(
        ThreatType threatType,
        ConfidenceLevel confidenceLevel
) {
}
