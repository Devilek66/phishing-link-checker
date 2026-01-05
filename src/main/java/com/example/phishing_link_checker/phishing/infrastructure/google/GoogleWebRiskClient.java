package com.example.phishing_link_checker.phishing.infrastructure.google;

import com.example.phishing_link_checker.phishing.infrastructure.google.dto.EvaluateUriRequest;
import com.example.phishing_link_checker.phishing.infrastructure.google.dto.EvaluateUriResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

@HttpExchange()
public interface GoogleWebRiskClient {

    @PostExchange("/v1eap1:evaluateUri")
    Mono<EvaluateUriResponse> evaluateUri(@RequestBody EvaluateUriRequest request);
}
