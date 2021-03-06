package com.nickz.crawler.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This class collects links starting from predefined seed link Once the class
 * is instantiated, the crawling begins, and after it is done, you can obtain
 * the link set via calling getLinks()
 *
 * @author Nikolay Zalosko
 */
public class WebCrawlerImpl implements WebCrawler {

	private final String seed;
	private final int linkDepth;
	private final int maxLinks;

	private final Set<String> links;

	public WebCrawlerImpl(String seed, int linkDepth, int maxLinks) {
		this.seed = seed;
		this.linkDepth = linkDepth;
		this.maxLinks = maxLinks;

		links = new LinkedHashSet<>(maxLinks);
		System.out.println("Collecting links...");
		this.populateLinks();
	}

	public Set<String> getLinks() {
		return this.links;
	}

	private void populateLinks() {
		UrlValidator validator = new UrlValidator();
		List<Set<String>> levels = new ArrayList<>();
		for (int i = 0; i <= this.linkDepth; i++) {
			levels.add(new HashSet<>());
		}

		// only 1 link on level 0 (root level)
		levels.get(0).add(seed);
		this.links.add(seed);

		// @formatter:off
		/* 
		 * l is current depth level
		 * j is current link count
		 */ 
		// @formatter:on
		for (int l = 0, j = 1; l < this.linkDepth && j < this.maxLinks; l++) {

			// current depth level link set
			Set<String> from = levels.get(l);

			// next depth level link set
			Set<String> to = levels.get(l + 1);

			for (Iterator<String> iterator = from.iterator(); iterator.hasNext();) {
				if (j > this.maxLinks) {
					break;
				}
				String link = iterator.next();
				Set<String> currentPageLinks = this.getPageLinksSet(link);
				if (currentPageLinks == null) {
					continue;
				}
				for (Iterator<String> iterator2 = currentPageLinks.iterator(); iterator2.hasNext();) {
					if (j > this.maxLinks) {
						break;
					}
					String linkToAddToNextLevel = iterator2.next();
					if (links.contains(linkToAddToNextLevel) || !validator.isValid(linkToAddToNextLevel)) {
						continue;
					}
					to.add(linkToAddToNextLevel);
					links.add(linkToAddToNextLevel);
					j++;
				}
			}
		}
	}

	private Set<String> getPageLinksSet(String url) {
		Document doc;
		try {
			doc = Jsoup.connect(url).timeout(3000).followRedirects(true).get();
		} catch (IOException e) {
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

}
