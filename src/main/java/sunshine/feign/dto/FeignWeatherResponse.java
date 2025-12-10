package sunshine.feign.dto;

public record FeignWeatherResponse(
	CurrentUnit current_units,
	Current current
) {

	public record CurrentUnit(
		String temperature_2m,
		String relative_humidity_2m,
		String apparent_temperature,
		String weather_code
	) {
	}

	public record Current(
		String temperature_2m,
		String relative_humidity_2m,
		String apparent_temperature,
		int weather_code
	) {
	}
}
