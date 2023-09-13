package cal.projeteq3.glucose.exception.request;

import cal.projeteq3.glucose.exception.APIException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends APIException{
	public BadRequestException(String message){
		super(HttpStatus.BAD_REQUEST, message);
	}

}