package sunshine.infrastructure.ai.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sunshine.ai")
public record LlmProperties(
    String provider
) {
}
