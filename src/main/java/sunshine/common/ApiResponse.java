package sunshine.common;

import java.time.Instant;

/**
 * API 응답을 표준화하는 레코드.
 *
 * @param <T> 응답 데이터 타입
 */
public record ApiResponse<T>(
    boolean success,
    T data,
    ErrorResponse error,
    Instant timestamp
) {
  public static <T> ApiResponse<T> ok(T data) {
    return new ApiResponse<>(true, data, null, Instant.now());
  }

  public static <T> ApiResponse<T> error(ErrorResponse error) {
    return new ApiResponse<>(false, null, error, Instant.now());
  }
}
