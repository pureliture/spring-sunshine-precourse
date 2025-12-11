package sunshine.api.weather;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sunshine.api.common.ApiResponse;
import sunshine.application.weather.WeatherDto;
import sunshine.application.weather.WeatherFacade;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    private final WeatherFacade weatherFacade;

    public WeatherController(WeatherFacade weatherFacade) {
        this.weatherFacade = weatherFacade;
    }

    @GetMapping
    public ApiResponse<WeatherDto> getWeather(@RequestParam String city) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City name is required");
        }
        return ApiResponse.ok(weatherFacade.getWeather(city));
    }
}
