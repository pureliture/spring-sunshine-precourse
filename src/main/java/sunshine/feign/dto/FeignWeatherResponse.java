package sunshine.feign.dto;

public record FeignWeatherResponse(
	CurrentUnit current_units,
	Current current
) {

	public record CurrentUnit(
		String temperature_2m,
		String relative_humidity_2m,
		String apparent_temperature,
		String weather_code,
		String precipitation,
		String rain,
		String showers,
		String snowfall,
		String wind_speed_10m,
		String wind_gusts_10m
	) {
	}

	public record Current(
		Double temperature_2m,
		Integer relative_humidity_2m,
		Double apparent_temperature,
		Integer weather_code,
		Double precipitation,
		Double rain,
		Double showers,
		Double snowfall,
		Double wind_speed_10m,
		Double wind_gusts_10m
	) {
	}

	// public record CurrentUnit(
	// 	String temperature_2m,
	// 	String relative_humidity_2m,
	// 	String apparent_temperature,
	// 	String weather_code
	// ) {
	// }
	//
	// public record Current(
	// 	String temperature_2m,
	// 	String relative_humidity_2m,
	// 	String apparent_temperature,
	// 	int weather_code
	// ) {
	// }
}
