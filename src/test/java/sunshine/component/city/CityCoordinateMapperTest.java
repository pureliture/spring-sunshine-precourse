package sunshine.component.city;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CityCoordinateMapperTest {

    private final CityCoordinateMapper mapper = new CityCoordinateMapper();

    @ParameterizedTest
    @CsvSource({
        "Seoul, 37.5665, 126.9780",
        "Tokyo, 35.6895, 139.6917",
        "NewYork, 40.7128, -74.0060",
        "Paris, 48.8566, 2.3522",
        "London, 51.5074, -0.1278",
        "seoul, 37.5665, 126.9780",
        "SEOUL, 37.5665, 126.9780"
    })
    @DisplayName("Supported cities return correct coordinates (case insensitive)")
    void getCoordinate_SupportedCities_ReturnsCoordinates(String city, double lat, double lon) {
        Coordinate coordinate = mapper.getCoordinate(city);
        assertThat(coordinate.latitude()).isEqualTo(lat);
        assertThat(coordinate.longitude()).isEqualTo(lon);
    }

    @Test
    @DisplayName("Unsupported city throws exception")
    void getCoordinate_UnsupportedCity_ThrowsException() {
        assertThatThrownBy(() -> mapper.getCoordinate("UnknownCity"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Unsupported city");
    }

    @Test
    @DisplayName("Null city throws exception")
    void getCoordinate_NullCity_ThrowsException() {
        assertThatThrownBy(() -> mapper.getCoordinate(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("City name cannot be null");
    }
}
