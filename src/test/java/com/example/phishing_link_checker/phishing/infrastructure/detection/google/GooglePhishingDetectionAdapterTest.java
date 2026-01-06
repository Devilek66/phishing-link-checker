package com.example.phishing_link_checker.phishing.infrastructure.detection.google;

import com.example.phishing_link_checker.phishing.domain.RiskLevel;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

@ActiveProfiles("test")
@SpringBootTest
class GooglePhishingDetectionAdapterTest {

    private WireMockServer wireMockServer;

    @Autowired
    GooglePhishingDetectionAdapter googlePhishingDetectionAdapter;

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(22222);
        wireMockServer.start();
    }

    @AfterEach
    void teardown() {
        wireMockServer.stop();
    }

    @Test
    void shouldReturnPhishingScore_whenGoogleWebRiskReturnsThreat() {
        var url = "http://phishing.test";
        wireMockServer.stubFor(
                WireMock.post("/v1eap1:evaluateUri")
                        .withHeader("Content-Type", WireMock.containing("application/json"))
                        .withRequestBody(WireMock.matchingJsonPath("$.uri", WireMock.equalTo(url)))
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody("""
                        {
                        "scores":
                            [
                                {
                                    "threatType": "SOCIAL_ENGINEERING",
                                    "confidenceLevel": "LOW"
                                }
                            ]
                        }
                        """)
                        )
        );

        StepVerifier.create(googlePhishingDetectionAdapter.checkUrl(url))
                .expectNextMatches(score -> score.getRiskLevel().equals(RiskLevel.LOW) && score.getUrl().equals(url))
                .verifyComplete();
    }
}