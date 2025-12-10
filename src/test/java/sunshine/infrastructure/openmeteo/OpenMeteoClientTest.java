package sunshine.infrastructure.openmeteo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class OpenMeteoClientTest {

    @Test
    @DisplayName("Successfully fetches weather data")
    void fetchWeather_Success() {
        // Given
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        OpenMeteoClient client = new OpenMeteoClient(new RestTemplateBuilder() {
            @Override
            public RestTemplate build() {
                return restTemplate;
            }
        });

        String jsonResponse = """
            {
                "current": {
                    "temperature_2m": 20.5,
                    "relative_humidity_2m": 60,
                    "apparent_temperature": 22.0,
                    "weather_code": 1,
                    "wind_speed_10m": 5.5
                }
            }
            """;

        server.expect(requestTo("https://api.open-meteo.com/v1/forecast?latitude=37.5665&longitude=126.978&current=temperature_2m,relative_humidity_2m,apparent_temperature,weather_code,wind_speed_10m"))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        // When
        OpenMeteoResponse response = client.fetchWeather(37.5665, 126.978);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.current().temperature2m()).isEqualTo(20.5);
        assertThat(response.current().weatherCode()).isEqualTo(1);
    }
}
