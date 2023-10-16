package cal.projeteq3.glucose.exception.badRequestException;

public class ManagerNotFoundException extends BadRequestException {

    public ManagerNotFoundException(Long id) {
        super("Manager with ID " + id + " does not exist.");
    }

}
