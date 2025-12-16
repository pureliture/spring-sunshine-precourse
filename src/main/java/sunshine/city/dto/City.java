package sunshine.city.dto;

import sunshine.feign.dto.FeignCoordinatesResponse;

public record City(
	String name,
	String lat,
	String lon
) {

	public static City generate(FeignCoordinatesResponse coordinates) {
		return new City(coordinates.name(), coordinates.lat(), coordinates.lon());
	}
}
