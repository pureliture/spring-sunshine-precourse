package sunshine.common.exception.model.infrastructure;

import sunshine.common.exception.model.ErrorCode;

/**
 * 데이터베이스 관련 작업 중 발생하는 예외.
 */
public class DatabaseException extends InfrastructureException {

  public DatabaseException(String message, Throwable cause) {
    super(ErrorCode.INTERNAL_SERVER_ERROR, message, cause);
  }

  public DatabaseException(String message) {
    super(ErrorCode.INTERNAL_SERVER_ERROR, message);
  }
}
