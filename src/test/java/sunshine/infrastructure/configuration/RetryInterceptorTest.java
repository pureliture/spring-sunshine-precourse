package sunshine.infrastructure.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import sunshine.infrastructure.restclient.configuration.RetryInterceptor;

class RetryInterceptorTest {

  @Test
  void shouldSucceedOnFirstAttempt() throws IOException {
    RetryInterceptor interceptor = new RetryInterceptor(3, 10);
    HttpRequest request = mock(HttpRequest.class);
    byte[] body = new byte[0];
    ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);
    ClientHttpResponse response = mock(ClientHttpResponse.class);

    when(execution.execute(request, body)).thenReturn(response);

    ClientHttpResponse result = interceptor.intercept(request, body, execution);

    assertEquals(response, result);
    verify(execution, times(1)).execute(request, body);
  }

  @Test
  void shouldRetryAndSucceed() throws IOException {
    RetryInterceptor interceptor = new RetryInterceptor(3, 10);
    HttpRequest request = mock(HttpRequest.class);
    byte[] body = new byte[0];
    ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);
    ClientHttpResponse response = mock(ClientHttpResponse.class);

    when(execution.execute(request, body))
        .thenThrow(new IOException("Fail"))
        .thenReturn(response);

    ClientHttpResponse result = interceptor.intercept(request, body, execution);

    assertEquals(response, result);
    verify(execution, times(2)).execute(request, body);
  }

  @Test
  void shouldRetryUpToMaxAttemptsAndFail() throws IOException {
    RetryInterceptor interceptor = new RetryInterceptor(3, 10);
    HttpRequest request = mock(HttpRequest.class);
    byte[] body = new byte[0];
    ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);

    when(execution.execute(request, body)).thenThrow(new IOException("Fail"));

    assertThrows(IOException.class, () -> {
      interceptor.intercept(request, body, execution);
    });

    verify(execution, times(3)).execute(request, body);
  }
}
