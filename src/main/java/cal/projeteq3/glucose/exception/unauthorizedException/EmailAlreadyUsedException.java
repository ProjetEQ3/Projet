package cal.projeteq3.glucose.exception.unauthorizedException;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException() {
        super("emailAlreadyUsed");
    }
}
