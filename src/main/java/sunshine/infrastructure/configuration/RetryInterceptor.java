package sunshine.infrastructure.configuration;

import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RetryInterceptor implements ClientHttpRequestInterceptor {

  private final int maxAttempts;
  private final long backoff;

  public RetryInterceptor(int maxAttempts, long backoff) {
    this.maxAttempts = maxAttempts;
    this.backoff = backoff;
  }

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request,
      byte[] body,
      ClientHttpRequestExecution execution
  ) throws IOException {
    return executeWithRetry(request, body, execution, 0);
  }

  private ClientHttpResponse executeWithRetry(
      HttpRequest request,
      byte[] body,
      ClientHttpRequestExecution execution,
      int attempt
  ) throws IOException {
    try {
      return execution.execute(request, body);
    } catch (IOException e) {
      if (attempt >= maxAttempts - 1) {
        throw e;
      }
      try {
        Thread.sleep(backoff);
      } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
        throw new IOException("Interrupted during retry backoff", ie);
      }
      return executeWithRetry(request, body, execution, attempt + 1);
    }
  }
}
