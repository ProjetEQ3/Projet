package cal.projeteq3.glucose.exception.badRequestException;

public class JobApplicationNotFoundException extends BadRequestException {

    public JobApplicationNotFoundException() {
        super("jobApplicationNotFound");
    }
}
