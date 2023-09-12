package cal.projeteq3.glucose.exception.unauthorisedException;

import cal.projeteq3.glucose.exception.APIException;
import org.springframework.http.HttpStatus;

public class UnauthorisedException extends APIException{
	public UnauthorisedException(String message){
		super(HttpStatus.UNAUTHORIZED, message);
	}
}
