package cal.projeteq3.glucose.exception.badRequestException;

public class UnauthorizedContractToSignException extends BadRequestException{
	public UnauthorizedContractToSignException(){
		super("You are not allowed to sign this contract");
	}

}
