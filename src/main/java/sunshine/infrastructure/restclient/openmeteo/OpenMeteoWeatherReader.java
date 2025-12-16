package sunshine.infrastructure.restclient.openmeteo;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;
import sunshine.common.exception.model.infrastructure.RestApiException;
import sunshine.weather.domain.Weather;
import sunshine.weather.domain.WeatherReader;

/**
 * OpenMeteo API를 사용하여 날씨 정보를 조회하는 어댑터.
 */
@Component
public class OpenMeteoWeatherReader implements WeatherReader {

  private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast";
  private final RestClient restClient;

  public OpenMeteoWeatherReader(RestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public Weather read(double latitude, double longitude) {
    OpenMeteoResponse response = this.fetchWeather(latitude, longitude);
    OpenMeteoResponse.Current current = response.current();
    return new Weather(
        current.temperature2m(),
        current.apparentTemperature(),
        current.relativeHumidity2m(),
        current.windSpeed10m(),
        current.weatherCode()
    );
  }

  private OpenMeteoResponse fetchWeather(double latitude, double longitude) {

    String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
            .queryParam("latitude", latitude)
            .queryParam("longitude", longitude)
            .queryParam("current", "temperature_2m,relative_humidity_2m,"
                    + "apparent_temperature,weather_code,wind_speed_10m")
            .build()
            .toUriString();

    try {
      return restClient.get()
              .uri(url)
              .retrieve()
              .body(OpenMeteoResponse.class);
    } catch (RestClientException e) {
      throw new RestApiException("OpenMeteo API 호출 중 오류가 발생했습니다.", e);
    }
  }
}
