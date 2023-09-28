package cal.projeteq3.glucose.exception.unauthorizedException;

import cal.projeteq3.glucose.exception.APIException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends APIException{
	public UnauthorizedException(String message){
		super(HttpStatus.UNAUTHORIZED, message);
	}
}
