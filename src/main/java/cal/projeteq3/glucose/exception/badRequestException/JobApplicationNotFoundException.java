package cal.projeteq3.glucose.exception.badRequestException;

public class JobApplicationNotFoundException extends BadRequestException {

//    TODO: add id to the message
    public JobApplicationNotFoundException(Long id) {
        super("jobApplicationNotFound");
    }
    public JobApplicationNotFoundException() {
        super("jobApplicationNotFound");
    }
}
