package cal.projeteq3.glucose.exception;

import org.springframework.http.HttpStatus;

public abstract class APIException extends RuntimeException{
	protected final HttpStatus status;
	protected final String message;

	public APIException(HttpStatus status, String message){
		this.status = status;
		this.message = message;
	}

	public APIException(String message, HttpStatus status, String message1){
		super(message);
		this.status = status;
		this.message = message1;
	}

	public HttpStatus getStatus(){
		return status;
	}

	@Override
	public String getMessage(){
		return message;
	}
}
