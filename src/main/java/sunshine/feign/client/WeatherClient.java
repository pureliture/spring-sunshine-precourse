package sunshine.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sunshine.config.FeignConfig;
import sunshine.feign.dto.FeignCoordinatesResponse;
import sunshine.feign.dto.FeignWeatherResponse;

@FeignClient(name = "weather", url = "https://api.open-meteo.com", configuration = FeignConfig.class)
public interface WeatherClient {

	@GetMapping(value = "/v1/forecast")
	FeignWeatherResponse getWeather(
		@RequestParam("latitude") String lat,
		@RequestParam("longitude") String lon,
		@RequestParam("current") String current
	);
}
