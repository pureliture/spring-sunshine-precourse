package sunshine.weather.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sunshine.city.domain.CityClient;
import sunshine.weather.domain.WeatherClient;
import sunshine.weather.enums.WmoCode;
import sunshine.feign.dto.FeignCoordinatesResponse;
import sunshine.feign.dto.FeignWeatherResponse;

@Service
@RequiredArgsConstructor
public class WeatherService {

	private final CityClient cityClient;
	private final WeatherClient weatherClient;

	public String getWeather(String city) {
		FeignCoordinatesResponse coordinates = cityClient.getCoordinates(city).getFirst();

		FeignWeatherResponse currentWeather = weatherClient.getWeather(coordinates.lat(), coordinates.lon(),
			"temperature_2m,relative_humidity_2m,apparent_temperature,weather_code");

		return String.format("%s의 현재 온도는 %s%s, 체감온도는 %s%s, 하늘 상태는 %s, 습도는 %s%s 입니다."
			,coordinates.name()
			,currentWeather.current().temperature_2m(), currentWeather.current_units().temperature_2m()
			,currentWeather.current().apparent_temperature(), currentWeather.current_units().apparent_temperature()
			,WmoCode.getDescription(currentWeather.current().weather_code())
			,currentWeather.current().relative_humidity_2m(), currentWeather.current_units().relative_humidity_2m()
		);
	}

}
