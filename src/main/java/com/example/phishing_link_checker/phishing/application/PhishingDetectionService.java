package com.example.phishing_link_checker.phishing.application;

import com.example.phishing_link_checker.phishing.domain.PhishingDetectionPort;
import org.springframework.stereotype.Service;

@Service
public class PhishingDetectionService {
    private final PhishingDetectionPort phishingDetectionPort;

    public PhishingDetectionService(PhishingDetectionPort phishingDetectionPort) {
        this.phishingDetectionPort = phishingDetectionPort;
    }
}
