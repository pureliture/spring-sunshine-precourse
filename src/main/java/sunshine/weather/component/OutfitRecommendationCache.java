package sunshine.weather.component;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
class OutfitRecommendationCache {

    private final Cache<String, String> cache;

    OutfitRecommendationCache() {
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .maximumSize(100)
                .build();
    }

    String getToday(String city) {
        return cache.getIfPresent(buildKey(city));
    }

    void putToday(String city, String recommendation) {
        cache.put(buildKey(city), recommendation);
    }

    private String buildKey(String city) {
        return city + ":" + LocalDate.now();
    }
}
