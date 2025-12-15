package sunshine.common.exception.model.infrastructure;

import sunshine.common.exception.model.ErrorCode;

/**
 * REST API 호출 중 발생하는 예외.
 */
public class RestApiException extends InfrastructureException {

  public RestApiException(String message, Throwable cause) {
    super(ErrorCode.EXTERNAL_API_ERROR, message, cause);
  }

  public RestApiException(String message) {
    super(ErrorCode.EXTERNAL_API_ERROR, message);
  }
}
