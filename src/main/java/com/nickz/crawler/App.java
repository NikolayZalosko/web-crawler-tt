package com.nickz.crawler;


public class App {
	public static void main(String[] args) {
		String[] terms = new String[] { "health", "politics", "world" };
		WebCrawler crawler = new WebCrawler("https://en.wikipedia.org/wiki/COVID-19_pandemic", terms);
		OutputPrinter outputPrinter = new OutputPrinter();
		
		outputPrinter.printToFile(crawler.getLinks(), "links.txt");
		
	}
}
