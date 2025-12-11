package sunshine.weather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sunshine.common.ApiResponse;
import sunshine.weather.dto.WeatherDto;
import sunshine.weather.service.WeatherService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    public ApiResponse<WeatherDto> getWeather(@RequestParam String city) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City name is required");
        }
        return ApiResponse.ok(weatherService.getWeather(city));
    }
}
