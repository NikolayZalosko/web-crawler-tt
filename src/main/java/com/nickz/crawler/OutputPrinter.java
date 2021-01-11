package com.nickz.crawler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

public class OutputPrinter {
	public void printToFile(Set<String> content, String fileName) {
		/*
		try {
			Files.deleteIfExists(Paths.get(fileName));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		File outputFile = new File(fileName);
		try (PrintWriter pw = new PrintWriter(outputFile)) {
			pw.print("");
			content.stream().forEach(pw::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
