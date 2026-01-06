package com.example.phishing_link_checker.phishing.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhishingScoreTest {

    @Test
    void shouldBlockReturnsFalseForLowRisk() {
        PhishingScore score = new PhishingScore("http://example.com", RiskLevel.LOW);
        assertFalse(score.shouldBlock());
    }

    @Test
    void shouldBlockReturnsTrueForMediumRisk() {
        PhishingScore score = new PhishingScore("http://example.com", RiskLevel.MEDIUM);
        assertTrue(score.shouldBlock());
    }

    @Test
    void shouldBlockReturnsTrueForHighRisk() {
        PhishingScore score = new PhishingScore("http://example.com", RiskLevel.HIGH);
        assertTrue(score.shouldBlock());
    }

    @Test
    void shouldBlockReturnsTrueForCriticalRisk() {
        PhishingScore score = new PhishingScore("http://example.com", RiskLevel.CRITICAL);
        assertTrue(score.shouldBlock());
    }

    @Test
    void gettersReturnCorrectValues() {
        PhishingScore score = new PhishingScore("http://example.com", RiskLevel.HIGH);
        assertEquals("http://example.com", score.getUrl());
        assertEquals(RiskLevel.HIGH, score.getRiskLevel());
    }
}