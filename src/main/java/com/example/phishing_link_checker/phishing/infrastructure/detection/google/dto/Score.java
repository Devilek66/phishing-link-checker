package com.example.phishing_link_checker.phishing.infrastructure.detection.google.dto;

public record Score(
        ThreatType threatType,
        ConfidenceLevel confidenceLevel
) {
}
