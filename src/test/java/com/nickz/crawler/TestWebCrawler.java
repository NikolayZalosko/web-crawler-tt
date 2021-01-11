package com.nickz.crawler;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class TestWebCrawler {

	@Test
	void testInit() {
		WebCrawler crawler = new WebCrawler("http://example.com", 1, 10);
		Set<String> links = crawler.getLinks();
		assertNotNull(links);
		assertFalse(links.isEmpty());
	}
}
