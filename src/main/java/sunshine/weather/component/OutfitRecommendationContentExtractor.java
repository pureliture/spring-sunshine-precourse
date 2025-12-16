package sunshine.weather.component;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;

@Component
class OutfitRecommendationContentExtractor {

    String extract(ChatResponse response) {
        if (response == null || response.getResult() == null || response.getResult().getOutput() == null) {
            return OutfitRecommendationDefaults.DEFAULT_RECOMMENDATION;
        }

        String text = response.getResult().getOutput().getText();
        if (text == null || text.isBlank()) {
            return OutfitRecommendationDefaults.DEFAULT_RECOMMENDATION;
        }

        return text;
    }
}
