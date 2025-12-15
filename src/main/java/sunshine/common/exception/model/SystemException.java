package sunshine.common.exception.model;

public class SystemException extends RuntimeException {

    private final ErrorCode errorCode;

    public SystemException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public SystemException(ErrorCode errorCode, String message) {
        this(errorCode, message, null);
    }

    public SystemException(ErrorCode errorCode) {
        this(errorCode, errorCode.getDefaultMessage(), null);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
