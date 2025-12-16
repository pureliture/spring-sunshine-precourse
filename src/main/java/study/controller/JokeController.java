package study.controller;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import study.ActorFilms;

@RestController
public class JokeController {

	private final ChatClient chatClient;

	public JokeController(ChatClient.Builder builder) {
		this.chatClient = builder.build();
	}

	@GetMapping("/joke1")
	public ActorFilms joke1(
		@RequestParam(defaultValue = "Leonardo DiCaprio") String actor
	) {

		var beanOutputConverter = new BeanOutputConverter<>(ActorFilms.class);
		var format = beanOutputConverter.getFormat();
		var userMessage = """
    Generate the filmography of 5 movies for {actor}.
    {format}
    """;
		var promptTemplate = new PromptTemplate(userMessage);
		var prompt = promptTemplate.render(Map.of("actor", actor, "format", format));

		return beanOutputConverter.convert(
			chatClient.prompt(prompt).call().chatResponse().getResult().getOutput().getText());
	}

	@GetMapping("/joke")
	public ChatResponse joke(
		@RequestParam(defaultValue = "Tell me a joke about {topic}") String message,
		@RequestParam(defaultValue = "programming") String topic
	) {
		PromptTemplate template = new PromptTemplate(message);
		String prompt = template.render(Map.of("topic", topic));

		return chatClient.prompt(prompt).call().chatResponse();
	}
}
