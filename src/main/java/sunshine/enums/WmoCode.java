package sunshine.enums;

import java.util.Set;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WmoCode {

	CLEAR_SKY(Set.of(0), "맑음"),

	MAINLY_CLEAR(Set.of(1, 2, 3), "흐림"),

	FOG(Set.of(45, 48), "안개"),

	DRIZZLE(Set.of(51, 53, 55), "이슬비"),

	FREEZING_DRIZZLE(Set.of(56, 57), "착빙이슬비"),

	RAIN(Set.of(61, 63, 65), "비"),

	FREEZING_RAIN(Set.of(66, 67), "얼음비"),

	SNOW_FALL(Set.of(71, 73, 75), "눈"),

	SNOW_GRAINS(Set.of(77), "싸락눈"),

	RAIN_SHOWERS(Set.of(80, 81, 82), "소나기"),

	SNOW_SHOWERS(Set.of(85, 86), "눈소나기"),

	THUNDERSTORM(Set.of(95), "뇌우"),

	THUNDERSTORM_HAIL(Set.of(96, 99), "우박뇌우");

	private final Set<Integer> codes;
	private final String description;

	public static String getDescription(int code) {
		for (WmoCode wmo : values()) {
			if (wmo.codes.contains(code)) {
				return wmo.description;
			}
		}
		return "알수없음";
	}
}
