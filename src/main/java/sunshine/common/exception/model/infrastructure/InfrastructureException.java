package sunshine.common.exception.model.infrastructure;

import sunshine.common.exception.model.ErrorCode;
import sunshine.common.exception.model.SystemException;

/**
 * 인프라스트럭처 계층에서 발생하는 모든 예외의 상위 클래스.
 */
public class InfrastructureException extends SystemException {

  public InfrastructureException(ErrorCode errorCode, String message, Throwable cause) {
    super(errorCode, message, cause);
  }

  public InfrastructureException(ErrorCode errorCode, String message) {
    this(errorCode, message, null);
  }

  public InfrastructureException(ErrorCode errorCode) {
    this(errorCode, errorCode.getDefaultMessage(), null);
  }
}
