package sunshine.infrastructure.openmeteo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import sunshine.common.BusinessException;
import sunshine.common.ErrorCode;

class OpenMeteoClientTest {

  @Test
  void fetchWeatherSuccessfully() {
    // given
    RestClient.Builder builder = RestClient.builder();
    MockRestServiceServer mockServer = MockRestServiceServer.bindTo(builder).build();
    RestClient restClient = builder.build();
    OpenMeteoClient client = new OpenMeteoClient(restClient);

    String jsonResponse = """
        {
            "latitude": 37.5,
            "longitude": 127.0,
            "generationtime_ms": 0.1,
            "utc_offset_seconds": 32400,
            "timezone": "Asia/Seoul",
            "timezone_abbreviation": "KST",
            "elevation": 30.0,
            "current_units": {
                "time": "iso8601",
                "interval": "seconds",
                "temperature_2m": "°C",
                "relative_humidity_2m": "%",
                "apparent_temperature": "°C",
                "weather_code": "wmo code",
                "wind_speed_10m": "km/h"
            },
            "current": {
                "time": "2023-10-27T12:00",
                "interval": 900,
                "temperature_2m": 15.0,
                "relative_humidity_2m": 60,
                "apparent_temperature": 14.5,
                "weather_code": 1,
                "wind_speed_10m": 3.5
            }
        }
    """;

    mockServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?latitude=37.5&longitude=127.0&current=temperature_2m,relative_humidity_2m,apparent_temperature,weather_code,wind_speed_10m"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

    // when
    OpenMeteoResponse response = client.fetchWeather(37.5, 127.0);

    // then
    assertNotNull(response);
    assertEquals(15.0, response.current().temperature2m());
    mockServer.verify();
  }

  @Test
  void throwsBusinessExceptionOnApiFailure() {
    // given
    RestClient.Builder builder = RestClient.builder();
    MockRestServiceServer mockServer = MockRestServiceServer.bindTo(builder).build();
    RestClient restClient = builder.build();
    OpenMeteoClient client = new OpenMeteoClient(restClient);

    mockServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?latitude=37.5&longitude=127.0&current=temperature_2m,relative_humidity_2m,apparent_temperature,weather_code,wind_speed_10m"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withServerError());

    // when & then
    BusinessException exception = assertThrows(BusinessException.class, () -> {
      client.fetchWeather(37.5, 127.0);
    });
    assertEquals(ErrorCode.EXTERNAL_API_ERROR, exception.getErrorCode());
    mockServer.verify();
  }
}
