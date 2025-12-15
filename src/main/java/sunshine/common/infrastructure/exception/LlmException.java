package sunshine.common.infrastructure.exception;

import sunshine.common.ErrorCode;

/**
 * LLM 서비스 호출 중 발생하는 예외.
 */
public class LlmException extends InfrastructureException {

  public LlmException(String message, Throwable cause) {
    super(ErrorCode.EXTERNAL_API_ERROR, message, cause);
  }

  public LlmException(String message) {
    super(ErrorCode.EXTERNAL_API_ERROR, message);
  }
}
