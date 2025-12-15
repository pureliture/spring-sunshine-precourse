package sunshine.common.infrastructure;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sunshine.common.ErrorResponse;
import sunshine.common.infrastructure.exception.InfrastructureException;

/**
 * 인프라스트럭처 예외를 처리하는 핸들러.
 */
@RestControllerAdvice
public class InfrastructureExceptionHandler {

  /**
   * InfrastructureException 및 하위 예외를 처리한다.
   */
  @ExceptionHandler(InfrastructureException.class)
  public ResponseEntity<ErrorResponse> handleInfrastructureException(InfrastructureException e) {
    return ResponseEntity
        .status(e.getErrorCode().getStatus())
        .body(new ErrorResponse(e.getErrorCode().name(), e.getMessage(), null));
  }
}
