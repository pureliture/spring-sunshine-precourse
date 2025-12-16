package sunshine.city.domain;

import org.springframework.stereotype.Component;

import sunshine.city.dto.City;


public interface CityClient {

	City getCity(String city);
}
