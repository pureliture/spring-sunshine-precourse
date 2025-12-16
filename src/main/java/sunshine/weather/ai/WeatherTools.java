package sunshine.weather.ai;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import sunshine.city.domain.CityClient;
import sunshine.city.dto.City;
import sunshine.weather.domain.WeatherClient;
import sunshine.weather.dto.Weather;

@Component
@RequiredArgsConstructor
public class WeatherTools {

	private final CityClient cityClient;
	private final WeatherClient weatherClient;

	@Tool(description = "도시명을 입력받아 해당 도시의 위도와 경도 좌표를 반환합니다. 도시는 물론, 권역 단위로도 날씨를 조회할 수 있다.")
	City getCoordinates(String city) {
		return cityClient.getCity(city);
	}

	@Tool(description = "위도와 경도 좌표를 입력받아 해당 위치의 현재 날씨 정보(기온, 습도, 풍속, 날씨 상태 등)를 반환합니다.")
	Weather getWeather(City coordinates) {
		return weatherClient.getWeather(coordinates.lat(), coordinates.lon());
	}

}
