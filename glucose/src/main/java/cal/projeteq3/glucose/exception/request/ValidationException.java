package cal.projeteq3.glucose.exception.request;

public class ValidationException extends BadRequestException{
	public ValidationException(String message){
		super(message);
	}

}