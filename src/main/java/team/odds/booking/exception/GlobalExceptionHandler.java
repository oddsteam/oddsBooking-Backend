package team.odds.booking.exception;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.odds.booking.model.ExceptionResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFound.class)
    public ResponseEntity<ExceptionResponse<ErrorDetails>> dataNotFoundException(DataNotFound ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getRequestURI(), HttpStatus.NOT_FOUND.getReasonPhrase());
        var errorRes = new ExceptionResponse<>(HttpStatus.NOT_FOUND.value(), errorDetails);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorRes);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse<ErrorDetails>> globalExceptionHandler(Exception ex, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
                request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        var errorRes = new ExceptionResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorDetails);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorRes);
    }

}
