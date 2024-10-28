package com.example.langchain_test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MarkdownParser {
	private static final Pattern HEADER_PATTERN = Pattern.compile("^(#+)\\s");

	private int calcHeaderDepth(String line) {
		var matcher = HEADER_PATTERN.matcher(line);
		return matcher.find() ? matcher.group(1).length() : -1;
	}

	public String parseMarkdown(String markdown) {
		String[] lines = markdown.split("\n");

		var currentHeaderDepth = 0; // depth of h1 is 1
		var headerDepthMap = new HashMap<Integer, Integer>();
		var headerStr = "";

		var intermediateText = new StringBuilder();

		for (var line : lines) {
			line = line.trim();
			if (line.isEmpty()) {
				continue;
			}
			var depth = calcHeaderDepth(line);
			if (depth < 0) {
				// line is paragraph
				intermediateText.append("{%s} %s\n".formatted(headerStr, line));
				continue;
			}

			// line is header
			if (depth < currentHeaderDepth) {
				for (int i = depth + 1; i <= currentHeaderDepth; i++) {
					headerDepthMap.remove(i);
				}
			}
			currentHeaderDepth = depth;
			
			headerDepthMap.put(depth, headerDepthMap.getOrDefault(depth, -1) + 1);

			headerStr = headerDepthMap.entrySet().stream()
					.sorted(Map.Entry.comparingByKey())
					.map(entry -> "h%d#%d".formatted(entry.getKey(), entry.getValue())) 
					.reduce((a, b) -> "%s,%s".formatted(a, b))
					.orElse("");
		}
		return intermediateText.toString();
	}
}