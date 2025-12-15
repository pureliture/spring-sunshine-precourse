package sunshine.weather.component;

import org.springframework.stereotype.Component;
import sunshine.weather.repository.WeatherCodeRepository;

/**
 * 날씨 정보를 바탕으로 사용자에게 제공할 요약 문장을 생성하는 컴포넌트.
 */
@Component
public class WeatherSummarizer {

  private final WeatherCodeRepository weatherCodeRepository;

  public WeatherSummarizer(WeatherCodeRepository weatherCodeRepository) {
    this.weatherCodeRepository = weatherCodeRepository;
  }

  /**
   * 주어진 날씨 정보를 바탕으로 요약 문장을 생성한다.
   */
  public String summarize(String city, double temp, double windSpeed, int weatherCode) {
    String weatherStatus = weatherCodeRepository.findById(weatherCode)
        .map(code -> code.getDescription())
        .orElse("알 수 없음");

    return String.format(
        "현재 %s의 기온은 %.1f°C이며, 풍속은 %.1fm/s입니다. 날씨는 %s입니다.",
        city, temp, windSpeed, weatherStatus
    );
  }
}
