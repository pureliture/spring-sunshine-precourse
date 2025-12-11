package sunshine.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import sunshine.city.component.Coordinate;

import java.util.Map;

/**
 * 도시 설정 정보를 관리하는 클래스.
 */
@Configuration
@ConfigurationProperties(prefix = "sunshine")
public class CityProperties {

    private Map<String, Coordinate> cities;

    public Map<String, Coordinate> getCities() {
        return cities;
    }

    public void setCities(Map<String, Coordinate> cities) {
        this.cities = cities;
    }
}
