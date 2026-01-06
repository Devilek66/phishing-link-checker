package com.example.phishing_link_checker.phishing.infrastructure.extraction;

import com.example.phishing_link_checker.phishing.domain.UrlExtractor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegexUrlExtractor implements UrlExtractor {

    private static final Pattern URL_PATTERN =
            Pattern.compile("https?://[^\\s,]+", Pattern.CASE_INSENSITIVE);

    @Override
    public List<String> extract(String message) {
        if (message == null || message.isBlank()) {
            return List.of();
        }

        List<String> urls = new ArrayList<>();
        Matcher matcher = URL_PATTERN.matcher(message);

        while (matcher.find()) {
            String rawUrl = matcher.group();
            urls.add(normalize(rawUrl));
        }

        return urls;
    }

    private String normalize(String url) {
        return url.replaceAll("[).,!?]+$", "");
    }
}
