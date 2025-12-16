package sunshine.weather.dto;

import sunshine.feign.dto.FeignWeatherResponse;

public record Weather(
	FeignWeatherResponse.CurrentUnit current_units,
	FeignWeatherResponse.Current current
) {

	public static Weather generate(FeignWeatherResponse weather) {
		return new Weather(
			weather.current_units(),
			weather.current()
		);
	}
}
