package sunshine.weather.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sunshine.weather.domain.OutfitAdvisor;
import sunshine.weather.domain.WeatherSummarizer;
import sunshine.weather.domain.WeatherSummarizerFactory;

@Service
@RequiredArgsConstructor
public class WeatherService {

	private final WeatherSummarizerFactory weatherSummarizerFactory;
	private final OutfitAdvisor outfitAdvisor;

	public String getWeatherInfo(String city, String flag) {

		WeatherSummarizer impl = weatherSummarizerFactory.createImpl(flag);
		String summarizedWeather = impl.getWeatherSummary(city);

		if ("oldshcool".equals(flag)) {
			return summarizedWeather;
		}

		String weather = outfitAdvisor.getWeather(summarizedWeather);

		return summarizedWeather + "<br><hr><br>" + weather;
	}
}
