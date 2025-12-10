package sunshine;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WeatherController {

	private final WeatherService weatherService;

	@GetMapping("/weather")
	public String getWeather(Model model, @RequestParam String city) {
		System.out.println(city);

		model.addAttribute("city", city);
		return "index";
	}

	@GetMapping("/")
	public String home() {

		return "index";
	}
}
