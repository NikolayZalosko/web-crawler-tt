package com.nickz.crawler;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nickz.crawler.preparer.ResultsPreparer;
import com.nickz.crawler.preparer.ResultsPreparerImpl;
import org.junit.jupiter.api.Test;

import com.nickz.crawler.model.PageResult;

public class TestResultsPreparerImpl {
	@Test
	void testInit() {
		Set<String> links = new HashSet<>();
		links.add("http://example.com/");

		List<String> terms = new ArrayList<>();
		terms.add("examples");

		ResultsPreparer preparer = new ResultsPreparerImpl(links, terms);
		List<PageResult> results = preparer.getResults();

		assertNotNull(results);
	}
}
