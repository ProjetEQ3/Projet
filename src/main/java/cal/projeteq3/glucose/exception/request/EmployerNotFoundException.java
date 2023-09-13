package cal.projeteq3.glucose.exception.request;

public class EmployerNotFoundException extends BadRequestException {

    public EmployerNotFoundException(Long id) {
        super("Employer with ID " + id + " does not exist.");
    }

}
