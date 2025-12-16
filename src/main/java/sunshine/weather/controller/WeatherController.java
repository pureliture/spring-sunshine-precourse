package sunshine.weather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import sunshine.weather.service.WeatherService;
import sunshine.weather.service.WeatherServiceFactory;
import sunshine.weather.service.WeatherServiceImpl;

@Controller
@RequiredArgsConstructor
public class WeatherController {

	private final WeatherServiceFactory weatherServiceFactory;

	@GetMapping("/weather")
	public String getWeather(
		Model model,
		@RequestParam("city") String city,
		@RequestParam(defaultValue = "oldshcool") String flag
	) {
		WeatherService impl = weatherServiceFactory.createImpl(flag);
		String result = impl.getWeather(city);

		model.addAttribute("result", result);
		model.addAttribute("selected", city);

		return "index";
	}

	@GetMapping("/")
	public String home() {

		return "index";
	}
}
