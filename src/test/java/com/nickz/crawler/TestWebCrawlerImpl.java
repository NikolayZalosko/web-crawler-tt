package com.nickz.crawler;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import com.nickz.crawler.crawler.WebCrawlerImpl;
import org.junit.jupiter.api.Test;

public class TestWebCrawlerImpl {

	@Test
	void testInit() {
		WebCrawlerImpl crawler = new WebCrawlerImpl("http://example.com", 1, 10);
		Set<String> links = crawler.getLinks();
		assertNotNull(links);
		assertFalse(links.isEmpty());
	}
}
