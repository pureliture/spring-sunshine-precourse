package sunshine.api.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
        var errorResponse = ErrorResponse.of(ex.getErrorCode(), ex.getMessage(), Map.of());
        return ResponseEntity.status(ex.getErrorCode().getStatus())
                .body(ApiResponse.error(errorResponse));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        var errorResponse = ErrorResponse.of(ErrorCode.VALIDATION_ERROR, ex.getMessage(), Map.of());
        return ResponseEntity.status(ErrorCode.VALIDATION_ERROR.getStatus())
                .body(ApiResponse.error(errorResponse));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        var errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage(), Map.of());
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiResponse.error(errorResponse));
    }
}
