package com.nickz.crawler.model;

import java.util.Map;

/**
 * Entity class that contains results of parsing links
 * 
 * @author Nikolay Zalosko
 */
public class PageResult implements Comparable<PageResult> {
	private final String link;
	private final Map<String, Integer> hitResults;

	public PageResult(String link, Map<String, Integer> hitResults) {
		this.link = link;
		this.hitResults = hitResults;
	}

	public String getLink() {
		return this.link;
	}

	public Map<String, Integer> getHitResults() {
		return this.hitResults;
	}

	public int totalHits() {
		return this.hitResults.values().stream().mapToInt(Integer::valueOf).sum();
	}

	@Override
	public int compareTo(PageResult o) {
		return this.totalHits() - o.totalHits();
	}
}
