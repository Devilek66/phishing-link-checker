package com.example.phishing_link_checker.phishing.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

public record VerificationRequest(
        @NotBlank String sender,
        @NotBlank String recipient,
        @NotBlank String message
) {
}
