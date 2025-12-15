package sunshine.infrastructure.configuration

import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import java.io.IOException

class RetryInterceptor(
    private val maxAttempts: Int,
    private val backoff: Long
) : ClientHttpRequestInterceptor {

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        return executeWithRetry(request, body, execution, 0)
    }

    private fun executeWithRetry(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution,
        attempt: Int
    ): ClientHttpResponse {
        try {
            return execution.execute(request, body)
        } catch (e: IOException) {
            return handleException(request, body, execution, attempt, e)
        }
    }

    private fun handleException(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution,
        attempt: Int,
        e: IOException
    ): ClientHttpResponse {
        if (attempt >= maxAttempts - 1) {
            throw e
        }
        Thread.sleep(backoff)
        return executeWithRetry(request, body, execution, attempt + 1)
    }
}
