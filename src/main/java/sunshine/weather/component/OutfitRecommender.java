package sunshine.weather.component;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
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

    private final ChatClientFactory chatClientFactory;
    private final OutfitRecommendationPrompt promptGenerator;
    private final LlmHistoryRepository llmHistoryRepository;

    // Key: City + Date, Value: Recommendation
    private final Cache<String, String> cache;

    public OutfitRecommender(ChatClientFactory chatClientFactory,
                             OutfitRecommendationPrompt promptGenerator,
                             LlmHistoryRepository llmHistoryRepository) {
        this.chatClientFactory = chatClientFactory;
        this.promptGenerator = promptGenerator;
        this.llmHistoryRepository = llmHistoryRepository;
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .maximumSize(100)
                .build();
    }

    public String recommend(String city, Weather weather) {
        String key = city + ":" + LocalDate.now();
        String cachedRecommendation = cache.getIfPresent(key);

        if (cachedRecommendation != null) {
            logUsage(true, null);
            return cachedRecommendation;
        }

        ChatClient client = chatClientFactory.getChatClient();
        String prompt = promptGenerator.generatePrompt(city, weather.temperature(), weather.maxTemperature(),
                                                       weather.precipitation(), weather.apparentTemperature(), weather.windSpeed());

        ChatResponse response = client.prompt().user(prompt).call().chatResponse();

        String content = response.getResult().getOutput().getText();

        cache.put(key, content);
        logUsage(false, response);

        return content;
    }

    private void logUsage(boolean isCached, ChatResponse response) {
        if (isCached) {
             log.info("Outfit Recommendation - Cached: Yes, Cost: 0");
             llmHistoryRepository.save(new LlmHistory("cache", "cache", 0, 0, 0, 0.0, true));
             return;
        }

        Usage usage = response.getMetadata().getUsage();
        String model = "unknown";
        int inputTokens = usage.getPromptTokens().intValue();
        int outputTokens = usage.getCompletionTokens().intValue();
        int totalTokens = usage.getTotalTokens().intValue();

        double estimatedCost = totalTokens * 0.0001;

        log.info("Outfit Recommendation - Cached: No, Input: {}, Output: {}, Total: {}, Cost: ${}",
                 inputTokens, outputTokens, totalTokens, String.format("%.6f", estimatedCost));

        llmHistoryRepository.save(new LlmHistory("ai", model, inputTokens, outputTokens, totalTokens, estimatedCost, false));
    }
}
