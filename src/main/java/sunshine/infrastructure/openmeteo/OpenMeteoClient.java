package sunshine.infrastructure.openmeteo;

/**
 * OpenMeteo API 클라이언트 인터페이스.
 */
public interface OpenMeteoClient {

  /**
   * 위도와 경도를 사용하여 날씨 정보를 조회한다.
   *
   * @param latitude  위도
   * @param longitude 경도
   * @return OpenMeteo 응답 객체
   */
  OpenMeteoResponse fetchWeather(double latitude, double longitude);
}
