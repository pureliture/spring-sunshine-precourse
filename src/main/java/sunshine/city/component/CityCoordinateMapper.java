package sunshine.city.component;

import java.util.Objects;
import org.springframework.stereotype.Component;
import sunshine.city.domain.City;
import sunshine.city.repository.CityRepository;
import sunshine.common.BusinessException;
import sunshine.common.ErrorCode;

/**
 * 도시 이름을 기반으로 해당 도시의 정보를 제공하는 컴포넌트.
 */
@Component
public class CityCoordinateMapper {

  private final CityRepository cityRepository;

  public CityCoordinateMapper(CityRepository cityRepository) {
    this.cityRepository = cityRepository;
  }

  /**
   * 주어진 도시 이름에 해당하는 좌표를 반환한다.
   */
  public Coordinate getCoordinate(String cityName) {
    if (Objects.isNull(cityName)) {
      throw new IllegalArgumentException("City name cannot be null");
    }

    City city = cityRepository.findByName(cityName.toLowerCase());
    if (city == null) {
      throw new BusinessException(ErrorCode.UNSUPPORTED_CITY);
    }

    return new Coordinate(city.getLatitude(), city.getLongitude());
  }
}
