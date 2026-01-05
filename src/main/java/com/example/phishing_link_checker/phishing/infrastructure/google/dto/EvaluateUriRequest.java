package com.example.phishing_link_checker.phishing.infrastructure.google.dto;

import java.util.List;

public record EvaluateUriRequest(String uri,
                                 List<ThreatType> threatTypes,
                                 Boolean allowScan) {
    public static EvaluateUriRequest EvaluateSocialEngineering(String uri)
    {
        return new EvaluateUriRequest(uri,List.of(ThreatType.SOCIAL_ENGINEERING), true);
    }
}
