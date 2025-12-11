package sunshine.city.component;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class CityCoordinateMapper {

    private final Map<String, Coordinate> cityCoordinates;

    public CityCoordinateMapper() {
        this.cityCoordinates = new HashMap<>();
        cityCoordinates.put("seoul", new Coordinate(37.5665, 126.9780));
        cityCoordinates.put("tokyo", new Coordinate(35.6895, 139.6917));
        cityCoordinates.put("newyork", new Coordinate(40.7128, -74.0060));
        cityCoordinates.put("paris", new Coordinate(48.8566, 2.3522));
        cityCoordinates.put("london", new Coordinate(51.5074, -0.1278));
    }

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
