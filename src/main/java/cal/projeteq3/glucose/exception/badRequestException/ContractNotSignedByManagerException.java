package cal.projeteq3.glucose.exception.badRequestException;

public class ContractNotSignedByManagerException extends BadRequestException{
	public ContractNotSignedByManagerException(){
		super("Contract not signed by manager");
	}

}
