package sunshine.mapper;

import org.mapstruct.Mapper;

import sunshine.dto.CoordinatesResponse;
import sunshine.feign.dto.FeignCoordinatesResponse;

@Mapper(componentModel = "spring")
public interface WeatherMapper {

	CoordinatesResponse toCoordinatesResponse(FeignCoordinatesResponse coordinates);
}
