package sunshine.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 날씨 코드 설정 정보를 관리하는 클래스.
 */
@Configuration
@ConfigurationProperties(prefix = "sunshine")
public class WeatherProperties {

    private Map<Integer, String> weatherCodes;

    public Map<Integer, String> getWeatherCodes() {
        return weatherCodes;
    }

    public void setWeatherCodes(Map<Integer, String> weatherCodes) {
        this.weatherCodes = weatherCodes;
    }
}
