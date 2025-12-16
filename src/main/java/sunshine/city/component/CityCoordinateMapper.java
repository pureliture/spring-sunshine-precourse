package sunshine.city.component;

import java.util.Objects;
import org.springframework.stereotype.Component;
import sunshine.city.domain.City;
import sunshine.city.repository.CityRepository;
import sunshine.common.exception.model.BusinessException;
import sunshine.common.exception.model.ErrorCode;
import sunshine.infrastructure.restclient.nominatim.NominatimClient;

/**
 * 도시 이름을 기반으로 해당 도시의 정보를 제공하는 컴포넌트.
 */
@Component
public class CityCoordinateMapper {

  private final CityRepository cityRepository;
  private final NominatimClient nominatimClient;

  public CityCoordinateMapper(CityRepository cityRepository, NominatimClient nominatimClient) {
    this.cityRepository = cityRepository;
    this.nominatimClient = nominatimClient;
  }

  /**
   * 주어진 도시 이름에 해당하는 좌표를 반환한다.
   */
  public Coordinate getCoordinate(String cityName) {
    if (Objects.isNull(cityName)) {
      throw new IllegalArgumentException("City name cannot be null");
    }

    // 1. Check DB
    return cityRepository.findByName(cityName.toLowerCase())
            .map(city -> new Coordinate(city.getLatitude(), city.getLongitude()))
            .orElseGet(() -> {
                // 2. Fallback to API
                Coordinate coord = nominatimClient.search(cityName);
                if (coord == null) {
                    throw new BusinessException(ErrorCode.UNSUPPORTED_CITY);
                }
                return coord;
            });
  }
}
