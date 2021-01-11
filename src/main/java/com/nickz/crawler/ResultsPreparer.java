package com.nickz.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.nickz.crawler.model.PageResult;

/**
 * This class finds occurrences of given terms in pages referred to by given links
 * Once the class is instantiated, the process begins,
 * and after it is done you can obtain results by calling getResults() method
 * 
 * @author Nikolay Zalosko
 */
public class ResultsPreparer {
	private final Set<String> links;
	private final List<String> terms;
	private List<PageResult> results;

	public ResultsPreparer(Set<String> links, List<String> terms) {
		this.links = links;
		this.terms = terms;
		
		System.out.println("Parsing pages...");
		this.prepaResults();
	}

	public List<PageResult> getResults() {
		return this.results;
	}
	
	private void prepaResults() {
		List<PageResult> results = new ArrayList<>();
		for (String link : this.links) {
			String text = this.parsePage(link);
			Map<String, Integer> hitResults = new LinkedHashMap<>();
			for (String term : this.terms) {
				Integer hits = this.findOccurences(text, term);
				hitResults.put(term, hits);
			}
			PageResult pageResult = new PageResult(link, hitResults);
			results.add(pageResult);
		}
		this.results = results;
	}
	
	private String parsePage(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).timeout(5000).get();
		} catch (IOException e) {
			return "";
		}
		return doc.body().text();
	}
	
	private int findOccurences(String text, String term) {
		int lastIndex = 0;
		int count = 0;
		String textLowCase = text.toLowerCase();
		String termLowCase = term.toLowerCase();
		while (lastIndex != -1) {
			lastIndex = textLowCase.indexOf(termLowCase, lastIndex);
			if (lastIndex != -1) {
				count++;
				lastIndex += termLowCase.length();
			}
		}
		return count;
	}
}
