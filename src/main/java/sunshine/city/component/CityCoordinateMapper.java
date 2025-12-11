package sunshine.city.component;

import org.springframework.stereotype.Component;
import sunshine.common.config.CityProperties;

import java.util.Map;

/**
 * 도시 이름을 기반으로 해당 도시의 좌표 정보를 제공하는 컴포넌트.
 * <p>
 * 지원하는 도시 목록과 좌표는 설정 파일에서 관리된다.
 */
@Component
public class CityCoordinateMapper {

    private final Map<String, Coordinate> cityCoordinates;

    public CityCoordinateMapper(CityProperties cityProperties) {
        this.cityCoordinates = cityProperties.getCities();
    }

    /**
     * 주어진 도시 이름에 해당하는 좌표를 반환한다.
     *
     * @param cityName 좌표를 조회할 도시 이름
     * @return 해당 도시의 좌표 (위도, 경도)
     * @throws IllegalArgumentException 도시 이름이 null이거나 지원하지 않는 도시일 경우
     */
    public Coordinate getCoordinate(String cityName) {
        if (cityName == null) {
            throw new IllegalArgumentException("City name cannot be null");
        }

        Coordinate coordinate = cityCoordinates.get(cityName.toLowerCase());

        if (coordinate == null) {
            throw new IllegalArgumentException("Unsupported city: " + cityName);
        }

        return coordinate;
    }
}
