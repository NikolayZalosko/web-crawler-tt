package com.nickz.crawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.nickz.crawler.model.PageResult;

public class OutputPrinter {

	private final List<PageResult> pageResults;
	private final List<String> terms;

	public OutputPrinter(List<PageResult> pageResults, List<String> terms) {
		this.pageResults = pageResults;
		this.terms = terms;
	}

	public void printResults() {
		try {
			// full stats to file
			List<String> headers = new ArrayList<>(terms);
			headers.add(0, "page_link");
			System.out.println("Writing to files...");
			this.printToCSVFile(this.pageResults, "full_stats.csv", headers);
			
			// top 10 pages to file
			List<PageResult> topResults = this.getTopPages(10);
			this.printToCSVFile(topResults, "top_10_pages.csv", headers);
			
			// top 10 pages to console
			System.out.println("\n----------------------TOP 10 PAGES BY TOTAL HITS----------------------");
			this.printToConsole(topResults);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	private void printToCSVFile(List<PageResult> pageResults, String fileName, List<String> headers) throws IOException {
		FileWriter out = new FileWriter(fileName);
		try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers.toArray(new String[0])))) {
			for (PageResult pageResult : pageResults) {
				List<String> record = new ArrayList<>();
				record.add(pageResult.getLink());

				for (Integer hitsNumber : pageResult.getHitResults().values()) {
					record.add(hitsNumber.toString());
				}

				printer.printRecord(record);
			}
		}
		System.out.println("File created: " + new File(fileName).getAbsolutePath());
	}
	
	private void printToConsole(List<PageResult> pageResults) {
		pageResults.stream().forEachOrdered(pageResult -> {
			System.out.print(pageResult.getLink());
			for (Integer hits : pageResult.getHitResults().values()) {
				System.out.print(" " + hits);
			}
			System.out.println();
		});
	}

	private List<PageResult> getTopPages(int limit) {
		return this.pageResults.stream().sorted(Comparator.reverseOrder()).limit(limit).collect(Collectors.toList());
	}
}
