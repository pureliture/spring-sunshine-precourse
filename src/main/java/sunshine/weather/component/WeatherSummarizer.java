package sunshine.weather.component;

import org.springframework.stereotype.Component;
import sunshine.common.config.WeatherProperties;

import java.util.Map;

/**
 * 날씨 정보를 바탕으로 사용자에게 제공할 요약 문장을 생성하는 컴포넌트.
 * <p>
 * 날씨 코드는 WMO(World Meteorological Organization) 표준을 따르며,
 * 코드에 대한 설명은 설정 파일에서 관리된다.
 */
@Component
public class WeatherSummarizer {

    private final Map<Integer, String> weatherCodes;

    public WeatherSummarizer(WeatherProperties weatherProperties) {
        this.weatherCodes = weatherProperties.getWeatherCodes();
    }

    /**
     * 주어진 날씨 정보를 바탕으로 요약 문장을 생성한다.
     *
     * @param city        도시 이름
     * @param temp        현재 기온
     * @param windSpeed   풍속
     * @param weatherCode 날씨 상태 코드
     * @return 날씨 요약 문장
     */
    public String summarize(String city, double temp, double windSpeed, int weatherCode) {
        String weatherStatus = weatherCodes.getOrDefault(weatherCode, "알 수 없음");

        return String.format(
            "현재 %s의 기온은 %.1f°C이며, 풍속은 %.1fm/s입니다. 날씨는 %s입니다.",
            city, temp, windSpeed, weatherStatus
        );
    }
}
