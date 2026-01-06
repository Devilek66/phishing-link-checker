package com.example.phishing_link_checker.subscription.domain;

public record PhoneNumber(String value) {

    public PhoneNumber {
        if (value == null || !value.matches("\\d{9,15}")) {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }
}