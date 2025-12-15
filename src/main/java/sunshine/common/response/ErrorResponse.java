package sunshine.common.response;

import sunshine.common.exception.model.ErrorCode;

import java.util.Map;

/**
 * 에러 응답을 표준화하는 레코드.
 */
public record ErrorResponse(
    String code,
    String message,
    Map<String, Object> details
) {
  public static ErrorResponse of(ErrorCode code, String message, Map<String, Object> details) {
    String msg = message != null ? message : code.getDefaultMessage();
    return new ErrorResponse(code.name(), msg, details);
  }
}
