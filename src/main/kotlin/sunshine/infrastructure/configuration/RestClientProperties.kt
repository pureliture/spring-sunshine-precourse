package sunshine.infrastructure.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "sunshine.client")
data class RestClientProperties(
    val connectTimeout: Long,
    val responseTimeout: Long,
    val retry: RetryProperties
) {
    data class RetryProperties(
        val maxAttempts: Int,
        val backoff: Long
    )
}
