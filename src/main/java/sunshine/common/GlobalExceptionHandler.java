package sunshine.common;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 처리기.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
    ErrorResponse errorResponse = ErrorResponse.of(ex.getErrorCode(), ex.getMessage(), Collections.emptyMap());
    return ResponseEntity.status(ex.getErrorCode().getStatus())
        .body(ApiResponse.error(errorResponse));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException ex) {
    Map<String, Object> details = ex.getConstraintViolations().stream()
        .collect(Collectors.toMap(
            v -> v.getPropertyPath().toString(),
            v -> v.getMessage() != null ? v.getMessage() : "Invalid value",
            (existing, replacement) -> replacement
        ));
    String message = ex.getConstraintViolations().stream().findFirst()
        .map(ConstraintViolation::getMessage)
        .orElse(ErrorCode.VALIDATION_ERROR.getDefaultMessage());

    ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.VALIDATION_ERROR, message, details);
    return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.getStatus())
        .body(ApiResponse.error(errorResponse));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
    ErrorResponse errorResponse = ErrorResponse.of(
        ErrorCode.VALIDATION_ERROR, ex.getMessage(), Collections.emptyMap()
    );
    return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.getStatus())
        .body(ApiResponse.error(errorResponse));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
    ErrorResponse errorResponse = ErrorResponse.of(
        ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage(), Collections.emptyMap()
    );
    return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
        .body(ApiResponse.error(errorResponse));
  }
}
