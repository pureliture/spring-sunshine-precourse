package sunshine.common;

import java.util.Map;

public record ErrorResponse(String code, String message, Map<String, Object> details) {
    public static ErrorResponse of(ErrorCode code, String message, Map<String, Object> details) {
        return new ErrorResponse(
            code.name(),
            message != null ? message : code.getDefaultMessage(),
            details
        );
    }
}
