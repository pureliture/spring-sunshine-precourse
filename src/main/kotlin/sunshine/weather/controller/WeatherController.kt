package sunshine.weather.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import sunshine.common.ApiResponse
import sunshine.weather.dto.WeatherDto
import sunshine.weather.service.WeatherService

/**
 * 날씨 정보 관련 API를 제공하는 컨트롤러.
 */
@RestController
@RequestMapping("/api/v1/weather")
class WeatherController(
    private val weatherService: WeatherService
) {

    /**
     * 특정 도시의 날씨 정보를 조회한다.
     *
     * @param city 도시 이름
     * @return 날씨 정보 응답
     * @throws IllegalArgumentException 도시 이름이 비어있을 경우
     */
    @GetMapping
    fun getWeather(@RequestParam city: String?): ApiResponse<WeatherDto> {
        require(!city.isNullOrBlank()) { "City name is required" }
        return ApiResponse.ok(weatherService.getWeather(city))
    }
}
