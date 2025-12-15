package sunshine.weather.controller;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sunshine.common.response.ApiResponse;
import sunshine.weather.dto.WeatherDto;
import sunshine.weather.service.WeatherService;

/**
 * 날씨 정보 관련 API를 제공하는 컨트롤러.
 */
@RestController
@RequestMapping("/api/v1/weather")
@Validated
public class WeatherController {

  private final WeatherService weatherService;

  public WeatherController(WeatherService weatherService) {
    this.weatherService = weatherService;
  }

  /**
   * 특정 도시의 날씨 정보를 조회한다.
   */
  @GetMapping
  public ApiResponse<WeatherDto> getWeather(
      @RequestParam
      @NotBlank(message = "City name is required")
      String city
  ) {
    return ApiResponse.ok(weatherService.getWeather(city));
  }
}
