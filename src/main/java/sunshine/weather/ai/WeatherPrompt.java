package sunshine.weather.ai;

import java.util.Map;

import sunshine.weather.dto.Weather;
import sunshine.weather.enums.WmoCode;

public record WeatherPrompt(
	Weather weather,
	String rawPrompt)
{

	public static final String PROMPT = """
		당신은 날씨 전문가입니다.
		아래 날씨 정보를 바탕으로 두 가지를 제공해주세요:
		1. 현재 날씨 요약
		2. 오늘의 복장 추천
		
		## 현재 날씨 정보
		- 도시: {cityName}
		- 현재 기온: {temperature}{temperatureUnit}
		- 체감 온도: {apparentTemperature}{apparentTemperatureUnit}
		- 습도: {humidity}{humidityUnit}
		- 날씨 상태: {weatherDescription}
		- 총 강수량: {precipitation}{precipitationUnit}
		- 비: {rain}{rainUnit}
		- 소나기: {showers}{showersUnit}
		- 적설량: {snowfall}{snowfallUnit}
		- 풍속: {windSpeed}{windSpeedUnit}
		- 돌풍: {windGusts}{windGustsUnit}
		
		위 정보를 바탕으로 날씨 요약과 복장 추천을 해주세요.
		응답은 한국어로 작성하고, 친근하고 실용적인 톤으로 작성해주세요.
		""";

	public static Map<String, Object> getMap(String cityName, Weather weather) {
		var current = weather.current();
		var units = weather.current_units();

		return Map.ofEntries(
			Map.entry("cityName", cityName),
			Map.entry("temperature", String.valueOf(current.temperature_2m())),
			Map.entry("temperatureUnit", units.temperature_2m()),
			Map.entry("apparentTemperature", String.valueOf(current.apparent_temperature())),
			Map.entry("apparentTemperatureUnit", units.apparent_temperature()),
			Map.entry("humidity", String.valueOf(current.relative_humidity_2m())),
			Map.entry("humidityUnit", units.relative_humidity_2m()),
			Map.entry("weatherDescription", WmoCode.getDescription(current.weather_code())),
			Map.entry("precipitation", String.valueOf(current.precipitation())),
			Map.entry("precipitationUnit", units.precipitation()),
			Map.entry("rain", String.valueOf(current.rain())),
			Map.entry("rainUnit", units.rain()),
			Map.entry("showers", String.valueOf(current.showers())),
			Map.entry("showersUnit", units.showers()),
			Map.entry("snowfall", String.valueOf(current.snowfall())),
			Map.entry("snowfallUnit", units.snowfall()),
			Map.entry("windSpeed", String.valueOf(current.wind_speed_10m())),
			Map.entry("windSpeedUnit", units.wind_speed_10m()),
			Map.entry("windGusts", String.valueOf(current.wind_gusts_10m())),
			Map.entry("windGustsUnit", units.wind_gusts_10m())
		);
	}
}
