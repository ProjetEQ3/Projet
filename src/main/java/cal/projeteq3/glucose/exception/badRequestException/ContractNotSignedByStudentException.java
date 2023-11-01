package cal.projeteq3.glucose.exception.badRequestException;

public class ContractNotSignedByStudentException extends BadRequestException{
	public ContractNotSignedByStudentException(){
		super("Contract not signed by student");
	}

}
