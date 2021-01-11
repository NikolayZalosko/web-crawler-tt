package com.nickz.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.validator.Validator;
import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {
	
	private final String seed;
	private final String[] terms;
	private final int linkDepth;
	private final int maxLinks;
	
	private Set<String> links;
	
	public WebCrawler(String seed, String[] terms, int linkDepth, int maxLinks) {
		this.seed = seed;
		this.terms = terms;
		this.linkDepth = linkDepth;
		this.maxLinks = maxLinks;
		
		links = new LinkedHashSet<>(maxLinks);
		this.populateLinks();
	}
	
	public WebCrawler(String seed, String[] terms) {
		this(seed, terms, 8, 10000);
	}
	
	public Set<String> getLinks() {
		return this.links;
	}
	
	
	private void populateLinks() {
		UrlValidator validator = new UrlValidator();
		List<Set<String>> levels = new ArrayList<>();
		for (int i = 0; i <= this.linkDepth; i++) {
			levels.add(new HashSet<String>());
		}
		
		// only 1 link on level 0 (root level)
		levels.get(0).add(seed);
		this.links.add(seed);
		
		/*
		 * l is current depth level
		 * j is current link count
		 * 
		 */
		for (int l = 0, j = 1; l < this.linkDepth && j < this.maxLinks; l++) {
			System.out.println("l = " + l + ", j = " + j);
			// current depth level
			Set<String> from = levels.get(l);
			
			// next depth level
			Set<String> to = levels.get(l + 1);
			
			for (Iterator<String> iterator = from.iterator(); iterator.hasNext(); ) {
				if (j > this.maxLinks) {
					break;
				}
				String link = iterator.next();
				Set<String> currentPageLinks = this.getPageLinksSet(link);
				if (currentPageLinks == null) {
					System.out.println("l = " + l + ", j = " + j);
					continue;
				}
				for (Iterator<String> iterator2 = currentPageLinks.iterator(); iterator2.hasNext(); ) {
					if (j > this.maxLinks) {
						break;
					}
					String linkToAddToNexlLevel = iterator2.next();
					if (links.contains(linkToAddToNexlLevel) || !validator.isValid(linkToAddToNexlLevel)) {
						continue;
					}
					to.add(linkToAddToNexlLevel);
					links.add(linkToAddToNexlLevel);
//					System.out.println(linkToAddToNexlLevel);
					j++;
				}
			}
		}
		
	}
	
	Set<String> getPageLinksSet(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (UnsupportedMimeTypeException e) {
			System.out.println("Unsupported content type of the page " + url);
			return null;
		} catch (IOException e) {
			System.out.println("Failed to connect to " + url + "\n" + e.getMessage());
			return null;
		} 
		
		
		Elements links = doc.getElementsByTag("a");
		Set<String> result = new HashSet<>();
		
		for (Element link : links) {
			String linkHref = link.attr("href");
			result.add(linkHref);
		}
		return result;
	}

	String parsePage(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			System.out.println("Failed to connect to " + url + "\n" + e.getMessage());
		}
		return doc.body().text();
	}

	int findOccurences(String text, String term) {
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
