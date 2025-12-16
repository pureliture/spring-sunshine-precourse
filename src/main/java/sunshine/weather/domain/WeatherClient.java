package sunshine.weather.domain;

import org.springframework.stereotype.Component;

import sunshine.feign.client.OpenMeteo;
import sunshine.weather.dto.Weather;

@Component
public interface WeatherClient {

	Weather getWeather(String lat, String lon);
}
