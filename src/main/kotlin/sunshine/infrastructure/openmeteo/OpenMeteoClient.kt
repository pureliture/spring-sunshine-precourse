package sunshine.infrastructure.openmeteo

import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClientException
import org.springframework.web.util.UriComponentsBuilder
import sunshine.common.BusinessException
import sunshine.common.ErrorCode

/**
 * OpenMeteo API와 통신하여 날씨 정보를 가져오는 클라이언트.
 */
@Component
class OpenMeteoClient(builder: RestClient.Builder) {

    private val restClient: RestClient = builder.build()

    /**
     * 위도와 경도를 사용하여 날씨 정보를 조회한다.
     *
     * @param latitude 위도
     * @param longitude 경도
     * @return 조회된 날씨 정보
     * @throws BusinessException API 호출 실패 시 발생
     */
    fun fetchWeather(latitude: Double, longitude: Double): OpenMeteoResponse {
        val url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
            .queryParam("latitude", latitude)
            .queryParam("longitude", longitude)
            .queryParam("current", "temperature_2m,relative_humidity_2m,apparent_temperature,weather_code,wind_speed_10m")
            .build()
            .toUriString()

        try {
            return restClient.get()
                .uri(url)
                .retrieve()
                .body(OpenMeteoResponse::class.java)!!
        } catch (e: RestClientException) {
            throw BusinessException(ErrorCode.EXTERNAL_API_ERROR, e)
        }
    }

    companion object {
        private const val BASE_URL = "https://api.open-meteo.com/v1/forecast"
    }
}
