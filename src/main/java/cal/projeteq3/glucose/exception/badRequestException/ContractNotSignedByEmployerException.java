package cal.projeteq3.glucose.exception.badRequestException;

public class ContractNotSignedByEmployerException extends BadRequestException{
	public ContractNotSignedByEmployerException(){
		super("empSignatureMissing");
	}

}
