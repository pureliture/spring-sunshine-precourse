package sunshine.infrastructure.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sunshine.client")
public record RestClientProperties(
    long connectTimeout,
    long responseTimeout,
    RetryProperties retry
) {
  public record RetryProperties(
      int maxAttempts,
      long backoff
  ) {
  }
}
