package sunshine.weather.domain;

/**
 * 날씨 정보를 나타내는 도메인 객체.
 *
 * @param temperature         온도 (섭씨)
 * @param apparentTemperature 체감 온도 (섭씨)
 * @param humidity            습도 (%)
 * @param windSpeed           풍속 (km/h)
 * @param weatherCode         날씨 코드
 * @param precipitation       강수량 (mm)
 * @param maxTemperature      최고 기온 (섭씨)
 */
public record Weather(
    double temperature,
    double apparentTemperature,
    int humidity,
    double windSpeed,
    int weatherCode,
    double precipitation,
    double maxTemperature
) {
}
