package com.nickz.crawler;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.nickz.crawler.model.PageResult;

public class ResultsPreparerTest {
	@Test
	void testInit() {
		Set<String> links = new HashSet<>();
		links.add("http://example.com/");

		List<String> terms = new ArrayList<>();
		terms.add("examples");

		ResultsPreparer preparer = new ResultsPreparer(links, terms);
		List<PageResult> results = preparer.getResults();

		assertNotNull(results);
	}
}
