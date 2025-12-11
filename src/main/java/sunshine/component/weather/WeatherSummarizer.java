package sunshine.component.weather;

import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class WeatherSummarizer {

    private static final Map<Integer, String> WEATHER_CODES = Map.ofEntries(
        Map.entry(0, "맑음"),
        Map.entry(1, "대체로 맑음"),
        Map.entry(2, "약간 흐림"),
        Map.entry(3, "흐림"),
        Map.entry(45, "안개"),
        Map.entry(48, "안개"),
        Map.entry(51, "이슬비"),
        Map.entry(53, "이슬비"),
        Map.entry(55, "이슬비"),
        Map.entry(61, "비"),
        Map.entry(63, "비"),
        Map.entry(65, "비"),
        Map.entry(71, "눈"),
        Map.entry(73, "눈"),
        Map.entry(75, "눈"),
        Map.entry(80, "소나기"),
        Map.entry(81, "소나기"),
        Map.entry(82, "소나기"),
        Map.entry(95, "천둥번개"),
        Map.entry(96, "천둥번개"),
        Map.entry(99, "천둥번개")
    );

    // WMO 날씨 해석 코드 (WW)
    // https://open-meteo.com/en/docs
    public String summarize(String city, double temp, double windSpeed, int weatherCode) {
        String weatherStatus = WEATHER_CODES.getOrDefault(weatherCode, "알 수 없음");

        return String.format(
            "현재 %s의 기온은 %.1f°C이며, 풍속은 %.1fm/s입니다. 날씨는 %s입니다.",
            city, temp, windSpeed, weatherStatus
        );
    }
}
