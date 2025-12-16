package sunshine.infrastructure.restclient.openmeteo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * OpenMeteo API 응답을 매핑하는 DTO.
 */
public record OpenMeteoResponse(Current current) {

  public record Current(
      @JsonProperty("temperature_2m")
      double temperature2m,
      @JsonProperty("relative_humidity_2m")
      int relativeHumidity2m,
      @JsonProperty("apparent_temperature")
      double apparentTemperature,
      @JsonProperty("weather_code")
      int weatherCode,
      @JsonProperty("wind_speed_10m")
      double windSpeed10m
  ) {
  }
}
