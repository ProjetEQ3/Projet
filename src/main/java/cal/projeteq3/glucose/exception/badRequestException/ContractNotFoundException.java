package cal.projeteq3.glucose.exception.badRequestException;

public class ContractNotFoundException extends BadRequestException{
    public ContractNotFoundException(){
        super("contractNotFound");
    }
}
