package cal.projeteq3.glucose.exception.unauthorisedException;

public class InvalidEmailOrPasswordException extends UnauthorisedException{
	public InvalidEmailOrPasswordException(){
		super("Invalid username or password");
	}
}
