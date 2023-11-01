package cal.projeteq3.glucose.exception.badRequestException;

public class JobApplicationHasAlreadyADecision extends BadRequestException{
    public JobApplicationHasAlreadyADecision() {
        super("decidedApplication");
    }
}
