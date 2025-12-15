package sunshine.weather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sunshine.common.BusinessException;
import sunshine.weather.dto.WeatherDto;
import sunshine.weather.service.WeatherService;

/**
 * 날씨 조회 UI를 제공하는 컨트롤러.
 */
@Controller
@RequestMapping("/weather")
public class WeatherPageController {

  private final WeatherService weatherService;

  public WeatherPageController(WeatherService weatherService) {
    this.weatherService = weatherService;
  }

  /**
   * 날씨 조회 페이지를 보여준다. city 파라미터가 있으면 날씨 정보를 조회하여 모델에 담는다.
   */
  @GetMapping
  public String weatherPage(@RequestParam(required = false) String city, Model model) {
    if (city == null || city.isBlank()) {
      return "weather";
    }

    try {
      WeatherDto weather = weatherService.getWeather(city);
      model.addAttribute("weather", weather);
    } catch (BusinessException e) {
      model.addAttribute("error", e.getErrorCode().getDefaultMessage());
    } catch (Exception e) {
      model.addAttribute("error", "An unexpected error occurred.");
    }

    return "weather";
  }
}
