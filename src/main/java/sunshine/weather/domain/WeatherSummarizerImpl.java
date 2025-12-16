package sunshine.weather.domain;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sunshine.city.domain.CityClient;
import sunshine.city.dto.City;
import sunshine.weather.dto.Weather;
import sunshine.weather.enums.WmoCode;

@Service
@RequiredArgsConstructor
public class WeatherSummarizerImpl implements WeatherSummarizer {

	private final CityClient cityClient;
	private final WeatherClient weatherClient;

	@Override
	public String getWeatherSummary(String city) {
		City coordinates = cityClient.getCity(city);
		Weather currentWeather = weatherClient.getWeather(coordinates.lat(), coordinates.lon());

		return String.format("%s의 현재 온도는 %s%s, 체감온도는 %s%s, 하늘 상태는 %s, 습도는 %s%s 입니다."
			,coordinates.name()
			,currentWeather.current().temperature_2m(), currentWeather.current_units().temperature_2m()
			,currentWeather.current().apparent_temperature(), currentWeather.current_units().apparent_temperature()
			,WmoCode.getDescription(currentWeather.current().weather_code())
			,currentWeather.current().relative_humidity_2m(), currentWeather.current_units().relative_humidity_2m()
		);
	}

	@Override
	public Boolean getServiceType(String flag) {
		// flag : oldschool
		return "oldschool".equals(flag);
	}

}
