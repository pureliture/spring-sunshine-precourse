package sunshine.weather.component;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class OutfitRecommendationPrompt {

    public String generatePrompt(String city, double temperature, double maxTemp, double precipitation, double feelsLike, double windSpeed) {
        return String.format("""
            Recommend an outfit for the following weather in %s.
            Date: %s
            Temperature: %.1f°C
            Max Temperature: %.1f°C
            Feels Like: %.1f°C
            Precipitation: %.1f mm
            Wind Speed: %.1f km/h

            Please provide a concise recommendation in Korean, considering the temperature range, precipitation, and wind.
            Mention if an umbrella or specific outerwear is needed.
            """, city, LocalDate.now(), temperature, maxTemp, feelsLike, precipitation, windSpeed);
    }
}
