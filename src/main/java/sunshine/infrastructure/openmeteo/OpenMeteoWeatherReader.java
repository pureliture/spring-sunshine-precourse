package sunshine.infrastructure.openmeteo;

import org.springframework.stereotype.Component;
import sunshine.weather.domain.Weather;
import sunshine.weather.domain.WeatherReader;

/**
 * OpenMeteo API를 사용하여 날씨 정보를 조회하는 어댑터.
 */
@Component
public class OpenMeteoWeatherReader implements WeatherReader {

  private final OpenMeteoClient openMeteoClient;

  public OpenMeteoWeatherReader(OpenMeteoClient openMeteoClient) {
    this.openMeteoClient = openMeteoClient;
  }

  @Override
  public Weather read(double latitude, double longitude) {
    OpenMeteoResponse response = openMeteoClient.fetchWeather(latitude, longitude);
    OpenMeteoResponse.Current current = response.current();
    return new Weather(
        current.temperature2m(),
        current.apparentTemperature(),
        current.relativeHumidity2m(),
        current.windSpeed10m(),
        current.weatherCode()
    );
  }
}
