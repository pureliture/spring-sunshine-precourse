package sunshine.weather.domain;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import sunshine.city.domain.CityClient;
import sunshine.city.dto.City;
import sunshine.weather.ai.WeatherSummarizePrompt;
import sunshine.weather.dto.Weather;

@Slf4j
@Service
public class WeatherAiSummarizerImpl implements WeatherSummarizer {

	private final ChatClient chatClient;
	private final CityClient cityClient;
	private final WeatherClient weatherClient;
	// private final WeatherTools weatherTools;

	public WeatherAiSummarizerImpl(ChatClient.Builder chatClient, CityClient cityClient, WeatherClient weatherClient) {
		this.chatClient = chatClient.build();
		this.cityClient = cityClient;
		this.weatherClient = weatherClient;
		// this.weatherTools = weatherTools;
	}

	@Override
	public String getWeatherSummary(String city) {
		City coordinates = cityClient.getCity(city);
		Weather currentWeather = weatherClient.getWeather(coordinates.lat(), coordinates.lon());

		PromptTemplate template = new PromptTemplate(WeatherSummarizePrompt.PROMPT);
		String prompt = template.render(WeatherSummarizePrompt.getMap(city, currentWeather));
		// String prompt = template.render(WeatherPrompt.getMap(city));

		// 한 번의 호출로 ChatResponse 받기
		ChatResponse response = chatClient.prompt(prompt)
			// .tools(new WeatherTools(cityClient, weatherClient))
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

	@Override
	public Boolean getServiceType(String flag) {
		// flag : newschool
		return "newschool".equals(flag);
	}
}
