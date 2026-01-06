package com.example.phishing_link_checker.phishing.domain;

public record Sms(String sender,
                  String recipient,
                  String message) {
}
