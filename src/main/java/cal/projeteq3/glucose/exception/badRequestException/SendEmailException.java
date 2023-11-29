package cal.projeteq3.glucose.exception.badRequestException;

public class SendEmailException extends BadRequestException{
	public SendEmailException(){
		super("Error sending email");
	}

}
