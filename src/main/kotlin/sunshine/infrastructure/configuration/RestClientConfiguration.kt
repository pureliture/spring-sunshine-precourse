package sunshine.infrastructure.configuration

import io.netty.channel.ChannelOption
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ReactorNettyClientRequestFactory
import org.springframework.http.client.ReactorResourceFactory
import org.springframework.web.client.RestClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Configuration
@EnableConfigurationProperties(RestClientProperties::class)
class RestClientConfiguration(
    private val properties: RestClientProperties
) {

    @Bean
    fun reactorResourceFactory(): ReactorResourceFactory {
        return ReactorResourceFactory()
    }

    @Bean
    fun restClientBuilder(reactorResourceFactory: ReactorResourceFactory): RestClient.Builder {
        val httpClient = HttpClient.create(reactorResourceFactory.connectionProvider)
            .runOn(reactorResourceFactory.loopResources)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, properties.connectTimeout.toInt())
            .responseTimeout(Duration.ofMillis(properties.responseTimeout))

        val requestFactory = ReactorNettyClientRequestFactory(httpClient)
        return RestClient.builder()
            .requestFactory(requestFactory)
            .requestInterceptor(RetryInterceptor(properties.retry.maxAttempts, properties.retry.backoff))
    }

    @Bean
    fun restClient(builder: RestClient.Builder): RestClient {
        return builder.build()
    }
}
