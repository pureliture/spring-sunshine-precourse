package sunshine.common;

/**
 * 비즈니스 로직에서 발생하는 예외를 처리하는 클래스.
 */
public class BusinessException extends RuntimeException {
  private final ErrorCode errorCode;

  public BusinessException(ErrorCode errorCode, Throwable cause) {
    super(errorCode.getDefaultMessage(), cause);
    this.errorCode = errorCode;
  }

  public BusinessException(ErrorCode errorCode) {
    this(errorCode, null);
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
