package cal.projeteq3.glucose.exception.badRequestException;

public class UnauthorizedContractToSignException extends BadRequestException{
	public UnauthorizedContractToSignException(){
		super("unauthorizedSigning");
	}

}
