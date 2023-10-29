package cal.projeteq3.glucose.exception.badRequestException;

public class JobApplicationHasAlreadyADecision extends BadRequestException{
    public JobApplicationHasAlreadyADecision() {
        super("Application already has a decision");
    }
}
