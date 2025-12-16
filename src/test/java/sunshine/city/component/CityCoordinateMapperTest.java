package sunshine.city.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sunshine.city.domain.City;
import sunshine.city.repository.CityRepository;
import sunshine.common.exception.model.BusinessException;
import sunshine.infrastructure.restclient.nominatim.NominatimClient;

@ExtendWith(MockitoExtension.class)
class CityCoordinateMapperTest {

  @Mock
  private CityRepository cityRepository;

  @Mock
  private NominatimClient nominatimClient;

  @InjectMocks
  private CityCoordinateMapper cityCoordinateMapper;

  @Test
  @DisplayName("DB에 도시가 존재하면 좌표를 반환한다")
  void getCoordinate_FromDB() {
    // given
    String cityName = "Seoul";
    City city = new City(1L, "seoul", 37.5665, 126.9780, null);
    given(cityRepository.findByName("seoul")).willReturn(Optional.of(city));

    // when
    Coordinate coordinate = cityCoordinateMapper.getCoordinate(cityName);

    // then
    assertThat(coordinate.latitude()).isEqualTo(37.5665);
    assertThat(coordinate.longitude()).isEqualTo(126.9780);
  }

  @Test
  @DisplayName("DB에 도시가 없으면 Nominatim API를 호출한다")
  void getCoordinate_FromAPI() {
    // given
    String cityName = "Busan";
    given(cityRepository.findByName("busan")).willReturn(Optional.empty());
    given(nominatimClient.search("Busan")).willReturn(new Coordinate(35.1796, 129.0756));

    // when
    Coordinate coordinate = cityCoordinateMapper.getCoordinate(cityName);

    // then
    assertThat(coordinate.latitude()).isEqualTo(35.1796);
    assertThat(coordinate.longitude()).isEqualTo(129.0756);
  }

  @Test
  @DisplayName("DB와 API 모두 도시가 없으면 예외가 발생한다")
  void getCoordinate_NotFound() {
    // given
    String cityName = "UnknownCity";
    given(cityRepository.findByName("unknowncity")).willReturn(Optional.empty());
    given(nominatimClient.search("UnknownCity")).willReturn(null);

    // when & then
    assertThatThrownBy(() -> cityCoordinateMapper.getCoordinate(cityName))
        .isInstanceOf(BusinessException.class);
  }
}
