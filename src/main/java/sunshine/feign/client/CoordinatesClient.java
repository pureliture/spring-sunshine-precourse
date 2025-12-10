package sunshine.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sunshine.config.FeignConfig;
import sunshine.feign.dto.FeignCoordinatesResponse;

@FeignClient(name = "coordinates", url = "https://nominatim.openstreetmap.org", configuration = FeignConfig.class)
public interface CoordinatesClient {

	@GetMapping(value = "/search?format=json&limit=1&accept-language=ko")
	List<FeignCoordinatesResponse> getCoordinates(
		@RequestParam("q") String city
	);
}
