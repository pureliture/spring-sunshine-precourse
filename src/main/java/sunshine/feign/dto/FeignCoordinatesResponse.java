package sunshine.feign.dto;

import java.util.List;

public record FeignCoordinatesResponse(
	String name,
	String lat,
	String lon
) {
}
