package sunshine.weather.service;

import org.springframework.stereotype.Service;
import sunshine.city.component.CityCoordinateMapper;
import sunshine.city.component.Coordinate;
import sunshine.weather.component.WeatherSummarizer;
import sunshine.weather.domain.Weather;
import sunshine.weather.domain.WeatherReader;
import sunshine.weather.dto.WeatherDto;

/**
 * 날씨 정보를 조회하고 요약하는 서비스.
 */
@Service
public class WeatherService {

  private final CityCoordinateMapper cityCoordinateMapper;
  private final WeatherReader weatherReader;
  private final WeatherSummarizer weatherSummarizer;

  public WeatherService(
      CityCoordinateMapper cityCoordinateMapper,
      WeatherReader weatherReader,
      WeatherSummarizer weatherSummarizer
  ) {
    this.cityCoordinateMapper = cityCoordinateMapper;
    this.weatherReader = weatherReader;
    this.weatherSummarizer = weatherSummarizer;
  }

  /**
   * 도시 이름을 입력받아 날씨 정보를 반환한다.
   */
  public WeatherDto getWeather(String city) {
    Coordinate coordinate = cityCoordinateMapper.getCoordinate(city);

    Weather weather = weatherReader.read(
        coordinate.latitude(),
        coordinate.longitude()
    );

    String summary = weatherSummarizer.summarize(
        city,
        weather.temperature(),
        weather.windSpeed(),
        weather.weatherCode()
    );

    return new WeatherDto(weather, summary);
  }
}
