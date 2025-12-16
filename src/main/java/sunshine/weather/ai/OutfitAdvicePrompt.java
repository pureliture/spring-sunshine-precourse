package sunshine.weather.ai;

import java.util.Map;

import sunshine.weather.dto.Weather;
import sunshine.weather.enums.WmoCode;

public record OutfitAdvicePrompt() {

	public static final String PROMPT = """
		당신은 복장 추천 전문가입니다.
		아래 날씨 정보를 바탕으로 오늘의 복장 추천해주세요

		## 현재 날씨 정보 요약
		- 날씨 요약: {summarizedWeather}

		위 정보를 바탕으로 복장 추천을 해주세요.
		응답은 한국어로 작성하고, 친근하고 실용적인 톤으로 작성해주세요.
		""";

	public static Map<String, Object> getMap(String summarizedWeather) {
		return Map.ofEntries(
			Map.entry("summarizedWeather", summarizedWeather)
		);
	}
}
