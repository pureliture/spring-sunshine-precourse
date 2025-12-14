package sunshine.weather.component

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import sunshine.weather.repository.WeatherCodeRepository

/**
 * 날씨 정보를 바탕으로 사용자에게 제공할 요약 문장을 생성하는 컴포넌트.
 */
@Component
class WeatherSummarizer(
    private val weatherCodeRepository: WeatherCodeRepository
) {

    /**
     * 주어진 날씨 정보를 바탕으로 요약 문장을 생성한다.
     */
    fun summarize(city: String, temp: Double, windSpeed: Double, weatherCode: Int): String {
        val weatherStatus = weatherCodeRepository.findByIdOrNull(weatherCode)
            ?.description
            ?: "알 수 없음"

        return "현재 %s의 기온은 %.1f°C이며, 풍속은 %.1fm/s입니다. 날씨는 %s입니다.".format(
            city, temp, windSpeed, weatherStatus
        )
    }
}
