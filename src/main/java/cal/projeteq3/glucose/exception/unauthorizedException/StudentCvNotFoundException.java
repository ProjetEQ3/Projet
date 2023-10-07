package cal.projeteq3.glucose.exception.unauthorizedException;

public class StudentCvNotFoundException extends UnauthorizedException {
    public StudentCvNotFoundException() {
        super("L'étudiant n'a pas de CV");
    }
}
