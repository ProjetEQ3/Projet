package cal.projeteq3.glucose.exception.unauthorizedException;

public class InvalidEmailOrPasswordException extends UnauthorizedException{
	public InvalidEmailOrPasswordException(){
		super("Invalid username or password");
	}
}
