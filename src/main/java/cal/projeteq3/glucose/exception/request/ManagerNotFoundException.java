package cal.projeteq3.glucose.exception.request;

public class ManagerNotFoundException extends BadRequestException {

    public ManagerNotFoundException(Long id) {
        super("Manager with ID " + id + " does not exist.");
    }

}
