package com.example.phishing_link_checker.phishing.domain;

import java.util.List;

public interface UrlExtractor {
    List<String> extract(String message);
}