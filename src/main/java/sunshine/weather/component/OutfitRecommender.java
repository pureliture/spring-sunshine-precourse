package sunshine.weather.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.stereotype.Component;
import sunshine.infrastructure.ai.configuration.ChatClientFactory;
import sunshine.infrastructure.ai.history.LlmHistory;
import sunshine.infrastructure.ai.history.LlmHistoryRepository;
import sunshine.weather.domain.Weather;

@Component
public class OutfitRecommender {

    private static final Logger log = LoggerFactory.getLogger(OutfitRecommender.class);
    static final String DEFAULT_RECOMMENDATION = OutfitRecommendationDefaults.DEFAULT_RECOMMENDATION;

    private final ChatClientFactory chatClientFactory;
    private final OutfitRecommendationPrompt promptGenerator;
    private final LlmHistoryRepository llmHistoryRepository;
    private final OutfitRecommendationCache cache;
    private final OutfitRecommendationValidator validator;

    public OutfitRecommender(ChatClientFactory chatClientFactory,
                             OutfitRecommendationPrompt promptGenerator,
                             LlmHistoryRepository llmHistoryRepository,
                             OutfitRecommendationCache cache,
                             OutfitRecommendationValidator validator) {
        this.chatClientFactory = chatClientFactory;
        this.promptGenerator = promptGenerator;
        this.llmHistoryRepository = llmHistoryRepository;
        this.cache = cache;
        this.validator = validator;
    }

    public String recommend(String city, Weather weather) {
        validator.validate(city, weather);
        String cachedRecommendation = cache.getToday(city);

        if (cachedRecommendation != null) {
            this.logUsage(true, null);
            return cachedRecommendation;
        }

        ChatResponse response = null;
        String content = DEFAULT_RECOMMENDATION;

        try {
            ChatClient client = chatClientFactory.getChatClient();
            String prompt = promptGenerator.generatePrompt(city, weather.temperature(), weather.maxTemperature(),
                                                           weather.precipitation(), weather.apparentTemperature(), weather.windSpeed());

            response = client.prompt().user(prompt).call().chatResponse();
            content = safeExtractContent(response);
        } catch (Exception ex) {
            log.warn("Failed to get outfit recommendation from AI. Using default recommendation.", ex);
        }

        cache.putToday(city, content);
        logUsage(false, response);

        return content;
    }

    private String safeExtractContent(ChatResponse response) {
        if (response == null || response.getResult() == null || response.getResult().getOutput() == null) {
            return DEFAULT_RECOMMENDATION;
        }

        String text = response.getResult().getOutput().getText();
        if (text == null || text.isBlank()) {
            return DEFAULT_RECOMMENDATION;
        }

        return text;
    }

    private void logUsage(boolean isCached, ChatResponse response) {
        if (isCached) {
             log.info("Outfit Recommendation - Cached: Yes, Cost: 0");
             llmHistoryRepository.save(new LlmHistory("cache", "cache", 0, 0, 0, 0.0, true));
             return;
        }

        try {
            Usage usage = (response != null && response.getMetadata() != null) ? response.getMetadata().getUsage() : null;
            String model = "unknown";
            int inputTokens = usage != null ? usage.getPromptTokens() : 0;
            int outputTokens = usage != null ? usage.getCompletionTokens() : 0;
            int totalTokens = usage != null ? usage.getTotalTokens() : 0;

            double estimatedCost = totalTokens * 0.0001;

            log.info("Outfit Recommendation - Cached: No, Input: {}, Output: {}, Total: {}, Cost: ${}",
                     inputTokens, outputTokens, totalTokens, String.format("%.6f", estimatedCost));

            llmHistoryRepository.save(new LlmHistory("ai", model, inputTokens, outputTokens, totalTokens, estimatedCost, false));
        } catch (Exception ex) {
            log.warn("Failed to log LLM usage for outfit recommendation.", ex);
        }
    }
}
