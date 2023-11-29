package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.exception.APIException;
import cal.projeteq3.glucose.exception.CustomErrorResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final HttpServletRequest request;
    
    @ExceptionHandler(APIException.class)
    public ResponseEntity<CustomErrorResponse> handleAPIException(APIException ex) {
        CustomErrorResponse response = CustomErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(ex.getStatus().value())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<CustomErrorResponse> handleSQLException(SQLException ex) {
        CustomErrorResponse response = CustomErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(673)
                .message("Erreur de la base de données")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<CustomErrorResponse> handleMessagingException(MessagingException ex) {
        CustomErrorResponse response = CustomErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.I_AM_A_TEAPOT.value())
                .message("Erreur lors de la notification par courriel: " + ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleOtherException(Exception ex) {
        CustomErrorResponse response = CustomErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(673)
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(673).body(response);
    }
}
