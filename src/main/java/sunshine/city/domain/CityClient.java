package sunshine.city.domain;

import org.springframework.stereotype.Component;

import sunshine.city.dto.City;
import sunshine.feign.client.Nominatim;

@Component
public interface CityClient extends Nominatim {

	default City getCity(String city) {
		var response = getCoordinates(city);

		if (response.isEmpty()) {
			throw new IllegalArgumentException("도시를 찾을 수 없습니다: " + city);
		}

		var coordinates = response.getFirst();
		return new City(
			coordinates.name(),
			coordinates.lat(),
			coordinates.lon()
		);
	}
}
