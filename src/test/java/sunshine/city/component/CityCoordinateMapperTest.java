package sunshine.city.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sunshine.common.exception.model.BusinessException;
import sunshine.common.exception.model.ErrorCode;

@SpringBootTest
@Transactional
class CityCoordinateMapperTest {

  @Autowired
  private CityCoordinateMapper cityCoordinateMapper;

  @Test
  @DisplayName("서울의 좌표를 정확히 반환해야 한다")
  void getCoordinate_ReturnsCorrectCoordinateForSeoul() {
    Coordinate coordinate = cityCoordinateMapper.getCoordinate("seoul");
    assertThat(coordinate).isNotNull();
    assertThat(coordinate.latitude()).isEqualTo(37.5665);
    assertThat(coordinate.longitude()).isEqualTo(126.9780);
  }

  @Test
  @DisplayName("대소문자 구분 없이 좌표를 반환해야 한다")
  void getCoordinate_ReturnsCorrectCoordinateIgnoreCase() {
    Coordinate coordinate = cityCoordinateMapper.getCoordinate("SEOUL");
    assertThat(coordinate).isNotNull();
    assertThat(coordinate.latitude()).isEqualTo(37.5665);
    assertThat(coordinate.longitude()).isEqualTo(126.9780);
  }

  @Test
  @DisplayName("지원하지 않는 도시는 예외를 던져야 한다")
  void getCoordinate_ThrowsExceptionForUnsupportedCity() {
    assertThatThrownBy(() -> cityCoordinateMapper.getCoordinate("InvalidCity"))
        .isInstanceOf(BusinessException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.UNSUPPORTED_CITY);
  }

  @Test
  @DisplayName("도시 이름이 null이면 예외를 던져야 한다")
  void getCoordinate_ThrowsExceptionForNullCity() {
    assertThatThrownBy(() -> cityCoordinateMapper.getCoordinate(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("City name cannot be null");
  }
}
