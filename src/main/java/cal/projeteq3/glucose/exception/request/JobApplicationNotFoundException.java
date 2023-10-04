package cal.projeteq3.glucose.exception.request;

public class JobApplicationNotFoundException extends BadRequestException {

    public JobApplicationNotFoundException(Long id) {
        super("JobApplication with ID " + id + " does not exist.");
    }
}
