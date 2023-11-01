package cal.projeteq3.glucose.exception.badRequestException;

public class EmployerNotFoundException extends BadRequestException {

    //    TODO: add id to the message
    public EmployerNotFoundException(Long id) {
        super("employerNotFound");
    }

}
