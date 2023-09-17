package cal.projeteq3.glucose.exception.request;

public class UserAlreadyExistsException extends BadRequestException{

	public UserAlreadyExistsException(String email){
		super("User with email " + email + " already exists");
	}

}
