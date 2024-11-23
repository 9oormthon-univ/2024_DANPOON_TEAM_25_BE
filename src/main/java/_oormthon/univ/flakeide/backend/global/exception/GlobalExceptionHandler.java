package _oormthon.univ.flakeide.backend.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException ex) {
        // 에러 응답 형식 생성
        ErrorResponse errorResponse = new ErrorResponse(
            false,
            ex.getStatusCode(),
            ex.getErrorCode(),
            ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getStatusCode()));
    }

    // 그 외 예외 처리

    public ResponseEntity<Object> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            false,
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            9999, // 커스텀 에러 코드 정의 (알 수 없는 에러)
            "An unexpected error occurred."
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
