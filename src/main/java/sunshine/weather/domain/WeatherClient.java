package sunshine.weather.domain;

import org.springframework.stereotype.Component;

import sunshine.feign.client.OpenMeteo;
import sunshine.weather.dto.Weather;

@Component
public interface WeatherClient extends OpenMeteo {

	String DEFAULT_CURRENT_PARAMS = "temperature_2m,relative_humidity_2m,apparent_temperature,weather_code";

	default Weather getWeather(String lat, String lon) {
		var response = getWeather(lat, lon, DEFAULT_CURRENT_PARAMS);

		return new Weather(
			response.current_units(),
			response.current()
		);
	}
}
