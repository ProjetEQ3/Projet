package cal.projeteq3.glucose.exception.unauthorizedException;

public class SignaturePrerequisiteNotMetException extends UnauthorizedException{
    public SignaturePrerequisiteNotMetException() {
        super("The signature prerequisit are not met for this operation. " +
                "The contract must be sign by the Employer, then the Student and end with the Director." +
                "Please check the contract status.");
    }
}
