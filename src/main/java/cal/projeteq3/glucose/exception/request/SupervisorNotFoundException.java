package cal.projeteq3.glucose.exception.request;

public class SupervisorNotFoundException extends BadRequestException {
    public SupervisorNotFoundException(Long supervisorId) {
        super("Supervisor with id " + supervisorId + " not found");
    }
}
