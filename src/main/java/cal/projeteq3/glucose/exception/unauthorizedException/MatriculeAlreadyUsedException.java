package cal.projeteq3.glucose.exception.unauthorizedException;

public class MatriculeAlreadyUsedException extends RuntimeException {
    public MatriculeAlreadyUsedException() {
        super("matriculeAlreadyUsed");
    }
}
