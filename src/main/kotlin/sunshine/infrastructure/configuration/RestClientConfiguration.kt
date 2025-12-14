package sunshine.infrastructure.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ReactorNettyClientRequestFactory
import org.springframework.http.client.ReactorResourceFactory
import org.springframework.web.client.RestClient
import reactor.netty.http.client.HttpClient

@Configuration
class RestClientConfiguration {

    @Bean
    fun reactorResourceFactory(): ReactorResourceFactory {
        return ReactorResourceFactory()
    }

    @Bean
    fun restClientBuilder(reactorResourceFactory: ReactorResourceFactory): RestClient.Builder {
        val httpClient = HttpClient.create(reactorResourceFactory.connectionProvider)
            .runOn(reactorResourceFactory.loopResources)

        val requestFactory = ReactorNettyClientRequestFactory(httpClient)
        return RestClient.builder()
            .requestFactory(requestFactory)
    }

    @Bean
    fun restClient(builder: RestClient.Builder): RestClient {
        return builder.build()
    }
}
