package sunshine.city.domain;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import sunshine.city.dto.City;
import sunshine.feign.client.Nominatim;
import sunshine.feign.dto.FeignCoordinatesResponse;

@Component
@RequiredArgsConstructor
public class CityClientImpl implements CityClient {

	private final Nominatim nominatim;

	public City getCity(String city) {
		var response = nominatim.getCoordinates(city);

		if (response.isEmpty()) {
			throw new IllegalArgumentException("도시를 찾을 수 없습니다: " + city);
		}

		FeignCoordinatesResponse coordinates = response.getFirst();

		return City.generate(coordinates);
	}
}
