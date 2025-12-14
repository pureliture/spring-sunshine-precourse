package sunshine.weather.service

import org.springframework.stereotype.Service
import sunshine.city.component.CityCoordinateMapper
import sunshine.infrastructure.openmeteo.OpenMeteoClient
import sunshine.weather.component.WeatherSummarizer
import sunshine.weather.dto.WeatherDto

/**
 * 날씨 정보를 조회하고 요약하는 서비스.
 */
@Service
class WeatherService(
    private val cityCoordinateMapper: CityCoordinateMapper,
    private val openMeteoClient: OpenMeteoClient,
    private val weatherSummarizer: WeatherSummarizer
) {

    /**
     * 도시 이름을 입력받아 날씨 정보를 반환한다.
     */
    fun getWeather(city: String): WeatherDto {
        val coordinate = cityCoordinateMapper.getCoordinate(city)

        val response = openMeteoClient.fetchWeather(
            coordinate.latitude,
            coordinate.longitude
        )

        val summary = weatherSummarizer.summarize(
            city,
            response.current.temperature2m,
            response.current.windSpeed10m,
            response.current.weatherCode
        )

        return WeatherDto(response.current, summary)
    }
}
