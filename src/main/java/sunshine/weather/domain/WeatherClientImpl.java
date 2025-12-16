package sunshine.weather.domain;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import sunshine.feign.client.OpenMeteo;
import sunshine.weather.dto.Weather;

@Component
@RequiredArgsConstructor
public class WeatherClientImpl implements WeatherClient {

	private final OpenMeteo openMeteo;

	// private final static String DEFAULT_CURRENT_PARAMS = "temperature_2m,relative_humidity_2m,apparent_temperature,weather_code";

	// 기온, 체감온도, 강수 여부, 바람 관련 파라미터
	private static final String DEFAULT_CURRENT_PARAMS =
		"temperature_2m,apparent_temperature,relative_humidity_2m,weather_code," +
			"precipitation,rain,showers,snowfall,wind_speed_10m,wind_gusts_10m";

	public Weather getWeather(String lat, String lon) {
		var response = openMeteo.getWeather(lat, lon, DEFAULT_CURRENT_PARAMS);

		return Weather.generate(response);
	}
}
