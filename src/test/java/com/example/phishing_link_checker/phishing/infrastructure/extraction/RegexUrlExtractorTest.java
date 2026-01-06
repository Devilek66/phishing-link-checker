package com.example.phishing_link_checker.phishing.infrastructure.extraction;

import com.example.phishing_link_checker.phishing.domain.UrlExtractor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegexUrlExtractorTest {

    private final UrlExtractor extractor = new RegexUrlExtractor();

    @Test
    void shouldExtractSingleUrl() {
        var result = extractor.extract(
                "test https://example.com test"
        );

        assertEquals(List.of("https://example.com"), result);
    }

    @Test
    void shouldRemoveTrailingDot() {
        var result = extractor.extract(
                "Link: https://example.com."
        );

        assertEquals(List.of("https://example.com"), result);
    }

    @Test
    void shouldHandleMultipleUrls() {
        var result = extractor.extract(
                "https://a.com and https://b.com/test,"
        );

        assertEquals(
                List.of("https://a.com", "https://b.com/test"),
                result
        );
    }

    @Test
    void shouldReturnEmptyWhenNoUrls() {
        assertTrue(extractor.extract("Brak linków").isEmpty());
    }
}