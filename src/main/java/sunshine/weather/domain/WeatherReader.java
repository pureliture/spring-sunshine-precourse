package sunshine.weather.domain;

/**
 * 날씨 정보를 조회하는 인터페이스 (Port).
 */
public interface WeatherReader {

  /**
   * 주어진 위도와 경도에 대한 날씨 정보를 조회한다.
   *
   * @param latitude  위도
   * @param longitude 경도
   * @return 날씨 정보
   */
  Weather read(double latitude, double longitude);
}
