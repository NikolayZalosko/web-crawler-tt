package com.nickz.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.validator.routines.UrlValidator;


public class App {
	
	private static final int DEFAULT_LINK_DEPTH = 8;
	private static final int DEFAULT_MAX_LINKS = 10000;
	
	static List<String> terms = null;
	static String seed = null;
	static Integer linkDepth = null;
	static Integer maxLinks = null;
	
	public static void main(String[] args) {
		readInputFromConsole();
		
		WebCrawler crawler = new WebCrawler(seed, linkDepth, maxLinks);
		ResultsPreparer preparer = new ResultsPreparer(crawler.getLinks(), terms);	
		OutputPrinter outputPrinter = new OutputPrinter(preparer.getResults(), terms);
		
		outputPrinter.printResults();
	}
	
	static void readInputFromConsole() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			// seed
			while (seed == null) {
				System.out.println("\n----------------------WEB CRAWLER----------------------");
				System.out.println("Enter URL to start from (so-called seed): ");
				UrlValidator validator = new UrlValidator();
				String inputSeed = reader.readLine();
				if (!validator.isValid(inputSeed)) {
					System.out.println("Not a valid URL. Try entering it again.");
				} else {
					seed = inputSeed;
				}
			}
			
			// terms
			while (terms == null) {
				System.out.println("Enter comma-separated terms (e. g. lorem,ipsum,whatever): ");
				String inputTerms = reader.readLine();
				if (inputTerms.isBlank()) {
					System.out.println("Enter valid terms!");
				} else {
					terms = Arrays.asList(inputTerms.trim().split(","));
				}
			}
				
			// link depth
			while (linkDepth == null) {
				System.out.println("Enter link depth (" + DEFAULT_LINK_DEPTH +" by default): ");
				String inputLinkDepthString = reader.readLine();
				if (inputLinkDepthString.isBlank()) {
					linkDepth = DEFAULT_LINK_DEPTH;
				} else if (!inputLinkDepthString.matches("\\d+")) {
					System.out.println("Not a valid link depth number, must be an integer! Try again.");
				} else {
					linkDepth = Integer.parseInt(inputLinkDepthString);
				}
			}
			
			// max links
			while (maxLinks == null) {
				System.out.println("Enter maximum links number (" + DEFAULT_MAX_LINKS + " by default): ");
				String inputMaxLinksString = reader.readLine();
				if (inputMaxLinksString.isBlank()) {
					maxLinks = DEFAULT_MAX_LINKS;
				} else if (!inputMaxLinksString.matches("\\d+")) {
					System.out.println("Not a valid maximum links number, must be an integer! Try again.");
				} else {
					maxLinks = Integer.parseInt(inputMaxLinksString);
				}	
			}	
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
