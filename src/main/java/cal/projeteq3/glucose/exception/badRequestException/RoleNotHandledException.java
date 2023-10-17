package cal.projeteq3.glucose.exception.badRequestException;

import cal.projeteq3.glucose.exception.APIException;
import org.springframework.http.HttpStatus;

public class RoleNotHandledException extends APIException {
    public RoleNotHandledException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
