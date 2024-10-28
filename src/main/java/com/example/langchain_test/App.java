package com.example.langchain_test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
	@Override
	public void start(Stage stage) {
		var intermediateText = "";

		// F:\PayaraHackathon\docs\sales_en.md ファイルをロード
		// ファイルの内容を markdown に格納
		try {
			var markdown = Files.readString(Path.of("F:\\PayaraHackathon\\docs\\sales_en.md"));
			var parser = new MarkdownParser();
			intermediateText =  parser.parseMarkdown(markdown);
		} catch (IOException e) {
			System.out.println("File not found:" + e.getMessage());
			Platform.exit();
		}
		System.out.println(intermediateText);

		/*
		var javaVersion = SystemInfo.javaVersion();
		var javafxVersion = SystemInfo.javafxVersion();
		
		var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
		var scene = new Scene(new StackPane(label), 640, 480);
		stage.setScene(scene);
		stage.show();
		
		// https://docs.langchain4j.dev/tutorials/rag/
		InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
			
		OpenAiEmbeddingModel model = OpenAiEmbeddingModel.builder()
		        .apiKey(System.getenv("OPENAI_API_KEY"))
		        .modelName("gpt-4o")
		        .build();
		
		TextSegment segment1 = TextSegment.from("I like football.");
		Embedding embedding1 = model.embed(segment1).content();
		
		embeddingStore.add(embedding1, segment1);
		
		// Response<Embedding> response = model.embed("I love Java");
		// Embedding embedding = response.content();
		
		String api_key = System.getenv("OPENAI_API_KEY");
		
		OpenAiChatModel model2 = OpenAiChatModel.builder()
				.apiKey(api_key)
				.modelName("gpt-4o")
				.build();
		String answer = model2.generate("こんにちは");
		System.out.println(answer);
		*/
	}

	public static void main(String[] args) {
		launch();
	}

}