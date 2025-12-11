package sunshine.application.weather;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sunshine.city.component.CityCoordinateMapper;
import sunshine.city.component.Coordinate;
import sunshine.weather.component.WeatherSummarizer;
import sunshine.infrastructure.openmeteo.OpenMeteoClient;
import sunshine.infrastructure.openmeteo.OpenMeteoResponse;
import sunshine.weather.dto.WeatherDto;
import sunshine.weather.service.WeatherService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private CityCoordinateMapper cityCoordinateMapper;
    @Mock
    private OpenMeteoClient openMeteoClient;
    @Mock
    private WeatherSummarizer weatherSummarizer;
    @InjectMocks
    private WeatherService weatherService;

    @Test
    @DisplayName("Successfully orchestrates weather retrieval")
    void getWeather_Success() {
        // Given
        String city = "Seoul";
        Coordinate coordinate = new Coordinate(37.5, 127.0);
        OpenMeteoResponse response = new OpenMeteoResponse(
            new OpenMeteoResponse.Current(20.0, 50, 21.0, 0, 5.0)
        );
        String summary = "Sunny day";

        given(cityCoordinateMapper.getCoordinate(city)).willReturn(coordinate);
        given(openMeteoClient.fetchWeather(coordinate.latitude(), coordinate.longitude()))
            .willReturn(response);
        given(weatherSummarizer.summarize(city, 20.0, 5.0, 0)).willReturn(summary);

        // When
        WeatherDto result = weatherService.getWeather(city);

        // Then
        assertThat(result.summary()).isEqualTo(summary);
        assertThat(result.temperature()).isEqualTo(20.0);
        assertThat(result.humidity()).isEqualTo(50);
    }
}
