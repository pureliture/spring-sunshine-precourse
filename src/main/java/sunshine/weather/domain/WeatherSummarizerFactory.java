package sunshine.weather.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WeatherSummarizerFactory {

	private final List<WeatherSummarizer> weatherSummarizerList;

	public WeatherSummarizer createImpl(String flag) {

		for (WeatherSummarizer weatherSummarizer : weatherSummarizerList) {
			if (weatherSummarizer.getServiceType(flag)) {
				return weatherSummarizer;
			}
		}

		throw new IllegalArgumentException("flag is invalid");
	}
}
