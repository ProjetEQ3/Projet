package cal.projeteq3.glucose.exception.badRequestException;

public class ContractAlreadySignedException extends BadRequestException{
	public ContractAlreadySignedException(){
		super("contractAlreadySigned");
	}

}
