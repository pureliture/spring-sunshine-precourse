package sunshine.weather.domain;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import sunshine.weather.ai.OutfitAdvicePrompt;

@Slf4j
@Service
public class OutfitAdvisor {

	private final ChatClient chatClient;

	public OutfitAdvisor(ChatClient.Builder chatClient) {
		this.chatClient = chatClient.build();
	}

	public String getWeather(String summarizedWeather) {

		PromptTemplate template = new PromptTemplate(OutfitAdvicePrompt.PROMPT);
		String prompt = template.render(OutfitAdvicePrompt.getMap(summarizedWeather));

		// 한 번의 호출로 ChatResponse 받기
		ChatResponse response = chatClient.prompt(prompt)
			.call()
			.chatResponse();

		// 로깅
		ChatResponseMetadata metadata = response.getMetadata();
		Usage usage = metadata.getUsage();
		log.info("Model: {}, Input Tokens: {}, Output Tokens: {}, Total Tokens: {}",
			metadata.getModel(),
			usage.getPromptTokens(),
			usage.getCompletionTokens(),
			usage.getTotalTokens());

		// content 반환
		return response.getResult().getOutput().getText();
	}
}
