package sunshine.weather.service;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WeatherServiceFactory {

	private final List<WeatherService> weatherServiceList;

	public WeatherService createImpl(String flag) {

		for (WeatherService weatherService : weatherServiceList) {
			if (weatherService.getServiceType(flag)) {
				return weatherService;
			}
		}

		throw new IllegalArgumentException("flag is invalid");
	}
}
