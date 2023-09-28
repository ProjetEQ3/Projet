package cal.projeteq3.glucose.exception.request;

import cal.projeteq3.glucose.exception.APIException;
import org.springframework.http.HttpStatus;

public class RoleNotHandled extends APIException {
    public RoleNotHandled(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
