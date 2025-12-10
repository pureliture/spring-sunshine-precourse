package sunshine.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sunshine.enums.WmoCode;
import sunshine.feign.client.CoordinatesClient;
import sunshine.feign.client.WeatherClient;
import sunshine.feign.dto.FeignCoordinatesResponse;
import sunshine.feign.dto.FeignWeatherResponse;

@Service
@RequiredArgsConstructor
public class WeatherService {

	private final CoordinatesClient coordinatesClient;
	private final WeatherClient weatherClient;

	public String getWeather(String city) {
		FeignCoordinatesResponse coordinates = coordinatesClient.getCoordinates(city).getFirst();
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
