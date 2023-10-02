package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.exception.APIException;
import cal.projeteq3.glucose.exception.CustomErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final HttpServletRequest request;

    @Autowired
    public CustomExceptionHandler(HttpServletRequest request) {
        this.request = request;
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<CustomErrorResponse> handleAPIException(APIException ex) {
        CustomErrorResponse response = CustomErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(ex.getStatus().value())
                .error(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleOtherException(Exception ex) {
        CustomErrorResponse response = CustomErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(673)
                .error(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(673).body(response);
    }
}
