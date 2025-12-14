package sunshine.weather.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import sunshine.common.BusinessException
import sunshine.weather.service.WeatherService

/**
 * 날씨 조회 UI를 제공하는 컨트롤러.
 */
@Controller
@RequestMapping("/weather")
class WeatherPageController(
    private val weatherService: WeatherService
) {

    /**
     * 날씨 조회 페이지를 보여준다.
     * city 파라미터가 있으면 날씨 정보를 조회하여 모델에 담는다.
     */
    @GetMapping
    fun weatherPage(@RequestParam(required = false) city: String?, model: Model): String {
        if (!city.isNullOrBlank()) {
            try {
                val weather = weatherService.getWeather(city)
                model.addAttribute("weather", weather)
            } catch (e: BusinessException) {
                model.addAttribute("error", e.errorCode.defaultMessage)
            } catch (e: Exception) {
                model.addAttribute("error", "An unexpected error occurred.")
            }
        }
        return "weather"
    }
}
