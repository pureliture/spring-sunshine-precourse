package sunshine.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ReactorNettyClientRequestFactory;
import org.springframework.http.client.ReactorResourceFactory;
import org.springframework.web.client.RestClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class RestClientConfiguration {

  @Bean
  public ReactorResourceFactory reactorResourceFactory() {
    return new ReactorResourceFactory();
  }

  @Bean
  public RestClient.Builder restClientBuilder(ReactorResourceFactory reactorResourceFactory) {
    HttpClient httpClient = HttpClient.create(reactorResourceFactory.getConnectionProvider())
        .runOn(reactorResourceFactory.getLoopResources());

    ReactorNettyClientRequestFactory requestFactory = new ReactorNettyClientRequestFactory(httpClient);
    return RestClient.builder()
        .requestFactory(requestFactory);
  }

  @Bean
  public RestClient restClient(RestClient.Builder builder) {
    return builder.build();
  }
}
