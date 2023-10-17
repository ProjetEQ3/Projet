package cal.projeteq3.glucose.exception.badRequestException;

public class UserAlreadyExistsException extends BadRequestException{

	public UserAlreadyExistsException(String email){
		super("User with email " + email + " already exists");
	}

}
