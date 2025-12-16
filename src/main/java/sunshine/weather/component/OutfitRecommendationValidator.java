package sunshine.weather.component;

import org.springframework.stereotype.Component;
import sunshine.weather.domain.Weather;

@Component
class OutfitRecommendationValidator {

    void validate(String city, Weather weather) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City must not be blank");
        }
        if (weather == null) {
            throw new IllegalArgumentException("Weather must not be null");
        }
    }
}
