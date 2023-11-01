package cal.projeteq3.glucose.exception.unauthorizedException;

public class StudentCvNotFoundException extends UnauthorizedException {
    public StudentCvNotFoundException() {
        super("Student CV not found");
    }
}
