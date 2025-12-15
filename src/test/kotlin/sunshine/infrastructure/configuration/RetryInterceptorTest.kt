package sunshine.infrastructure.configuration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpResponse
import java.io.IOException

class RetryInterceptorTest {

    @Test
    fun `should succeed on first attempt`() {
        val interceptor = RetryInterceptor(3, 10)
        val request = mock(HttpRequest::class.java)
        val body = ByteArray(0)
        val execution = mock(ClientHttpRequestExecution::class.java)
        val response = mock(ClientHttpResponse::class.java)

        `when`(execution.execute(request, body)).thenReturn(response)

        val result = interceptor.intercept(request, body, execution)

        assertEquals(response, result)
        verify(execution, times(1)).execute(request, body)
    }

    @Test
    fun `should retry and succeed`() {
        val interceptor = RetryInterceptor(3, 10)
        val request = mock(HttpRequest::class.java)
        val body = ByteArray(0)
        val execution = mock(ClientHttpRequestExecution::class.java)
        val response = mock(ClientHttpResponse::class.java)

        `when`(execution.execute(request, body))
            .thenThrow(IOException("Fail"))
            .thenReturn(response)

        val result = interceptor.intercept(request, body, execution)

        assertEquals(response, result)
        verify(execution, times(2)).execute(request, body)
    }

    @Test
    fun `should retry up to max attempts and fail`() {
        val interceptor = RetryInterceptor(3, 10)
        val request = mock(HttpRequest::class.java)
        val body = ByteArray(0)
        val execution = mock(ClientHttpRequestExecution::class.java)

        `when`(execution.execute(request, body)).thenThrow(IOException("Fail"))

        assertThrows(IOException::class.java) {
            interceptor.intercept(request, body, execution)
        }

        verify(execution, times(3)).execute(request, body)
    }
}
