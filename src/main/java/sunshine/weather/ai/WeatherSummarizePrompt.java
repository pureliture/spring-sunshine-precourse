package sunshine.weather.ai;

import java.util.Map;

import sunshine.weather.dto.Weather;
import sunshine.weather.enums.WmoCode;

public record WeatherSummarizePrompt(
	Weather weather,
	String rawPrompt)
{
	// public static final String PROMPT = """
	//
	// 	당신은 날씨 전문가입니다. 반드시 제공된 도구(Tool)를 사용해서 정보를 조회해야 합니다.
	//
	// 	## 요청 도시: {cityName}
	//
	// 	## 필수 수행 단계:
	// 	1. getCoordinates 도구를 호출하여 "{cityName}"의 위도/경도를 조회하세요.
	// 	2. 조회된 좌표로 getWeather 도구를 호출하여 현재 날씨를 조회하세요.
	// 	3. 조회된 실제 날씨 데이터를 기반으로 아래 내용을 작성하세요:
	// 	   - 현재 날씨 요약
	// 	   - 오늘의 복장 추천
	//
	// 	도구를 호출하지 않고 응답하지 마세요. 반드시 실제 데이터를 조회한 후 응답하세요.
	// 	응답은 한국어로, 친근하고 실용적인 톤으로 작성해주세요.
	// 	""";

	public static final String PROMPT = """
		당신은 날씨 전문가입니다.
		아래 날씨 정보를 바탕으로 한 가지를 제공해주세요:
		현재 날씨 요약

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

		위 정보를 바탕으로 날씨 요약 해주세요.
		응답은 한국어로 작성해주세요. 아래 예시와 같은 형태로 문장을 작성해주세요.
		(ex: 송파구의 현재 온도는 7.2°C, 체감온도는 4.4°C, 하늘 상태는 흐림, 습도는 75% 입니다.)
		
		주의사항 - 날씨 요약정보만 제공하는 당신의 역할을 소개할 필요는 없습니다.
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
