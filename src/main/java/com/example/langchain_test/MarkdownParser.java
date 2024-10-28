package com.example.langchain_test;

import java.util.HashMap;

public class MarkdownParser {
	private boolean isHeader(String line) {
        return line.startsWith("#");
    }
	
	private int calcHeaderDepth(String line) {
		var depth = 0;
		try {
			while (line.charAt(depth) == '#') {
				depth++;
			}
		} catch (IndexOutOfBoundsException e) {
			return -1;
		}
		return depth;
	}

	public String parseMarkdown(String markdown) {
        String[] lines = markdown.split("\n");

        var currentHeaderDepth = 1;
        var headerDepthMap = new HashMap<Integer, Integer>(); 
        var headerStr = "";
        
        var intermediateText = "";
        
        for (var line : lines) {
            line = line.trim();

            if (isHeader(line)) {
            	var depth = calcHeaderDepth(line);
				if (depth < 0) {
					continue;
				}
				if (depth < currentHeaderDepth) {
					for (int i = depth + 1; i <= currentHeaderDepth; i++) {
						headerDepthMap.remove(i);
					}
				}
				if (!headerDepthMap.containsKey(depth)) {
					headerDepthMap.put(depth, 0);
				}
				else {
					headerDepthMap.put(depth, headerDepthMap.get(depth) + 1);
				}
				currentHeaderDepth = depth;
		
				depth = 1;
				headerStr = "";
				while(headerDepthMap.containsKey(depth)) {
					headerStr += "h" + depth + "#" + headerDepthMap.get(depth) + ",";
                    depth++;
				}
				headerStr = headerStr.substring(0, headerStr.length() - 1);
            }
            else if(line.isEmpty()) {
            	// skip empty lines
            }
			else {
				intermediateText += "{" + headerStr + "} " + line + "\n";
			}
        }
        return intermediateText;        
    }
}