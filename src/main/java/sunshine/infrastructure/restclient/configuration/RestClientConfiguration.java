package sunshine.infrastructure.restclient.configuration;

import io.netty.channel.ChannelOption;
import java.time.Duration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ReactorNettyClientRequestFactory;
import org.springframework.http.client.ReactorResourceFactory;
import org.springframework.web.client.RestClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@EnableConfigurationProperties(RestClientProperties.class)
public class RestClientConfiguration {

  private final RestClientProperties properties;

  public RestClientConfiguration(RestClientProperties properties) {
    this.properties = properties;
  }

  @Bean
  public ReactorResourceFactory reactorResourceFactory() {
    return new ReactorResourceFactory();
  }

  @Bean
  public RestClient.Builder restClientBuilder(ReactorResourceFactory reactorResourceFactory) {
    HttpClient httpClient = HttpClient.create(reactorResourceFactory.getConnectionProvider())
        .runOn(reactorResourceFactory.getLoopResources())
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) properties.connectTimeout())
        .responseTimeout(Duration.ofMillis(properties.responseTimeout()));

    ReactorNettyClientRequestFactory requestFactory = new ReactorNettyClientRequestFactory(httpClient);
    return RestClient.builder()
        .requestFactory(requestFactory)
        .requestInterceptor(new RetryInterceptor(
            properties.retry().maxAttempts(),
            properties.retry().backoff()
        ));
  }

  @Bean
  public RestClient restClient(RestClient.Builder builder) {
    return builder.build();
  }
}
