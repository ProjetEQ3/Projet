package cal.projeteq3.glucose.exception.request;

public class ContractNotFoundException extends BadRequestException{

    public ContractNotFoundException(Long id) {
        super("Contract with id " + id + " not found");
    }
}
