package cal.projeteq3.glucose.exception.badRequestException;

public class ContractNotSignedByStudentException extends BadRequestException{
	public ContractNotSignedByStudentException(){
		super("studentSignatureMissing");
	}

}
