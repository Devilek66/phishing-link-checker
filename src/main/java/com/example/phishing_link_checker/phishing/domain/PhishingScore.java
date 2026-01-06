package com.example.phishing_link_checker.phishing.domain;

public class PhishingScore {
    private final String url;
    private final RiskLevel riskLevel;

    public PhishingScore(String url, RiskLevel riskLevel) {
        this.url = url;
        this.riskLevel = riskLevel;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public String getUrl() {
        return url;
    }

    public boolean shouldBlock() {
        return switch (riskLevel) {
            case MEDIUM, HIGH, CRITICAL -> true;
            default -> false;
        };
    }
}
