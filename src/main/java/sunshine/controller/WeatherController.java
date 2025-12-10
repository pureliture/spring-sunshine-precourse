package sunshine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import sunshine.service.WeatherService;

@Controller
@RequiredArgsConstructor
public class WeatherController {

	private final WeatherService weatherService;

	@GetMapping("/weather")
	public String getWeather(Model model, @RequestParam("city") String city) {
		String weather = weatherService.getWeather(city);

		model.addAttribute("weather", weather);
		model.addAttribute("selected", city);

		return "index";
	}

	@GetMapping("/")
	public String home() {

		return "index";
	}
}
