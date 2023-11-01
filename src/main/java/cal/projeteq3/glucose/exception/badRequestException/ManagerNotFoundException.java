package cal.projeteq3.glucose.exception.badRequestException;

public class ManagerNotFoundException extends BadRequestException {

//    TODO: add id to the message
    public ManagerNotFoundException(Long id) {
        super("managerNotFound");
    }

}
