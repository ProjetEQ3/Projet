package cal.projeteq3.glucose.exception.unauthorizedException;

public class SignaturePrerequisitNotMet extends UnauthorizedException{
    public SignaturePrerequisitNotMet() {
        super("The signature prerequisit are not met for this operation. " +
                "The contract must be sign by the Employer, then the Student and end with the Director." +
                "Please check the contract status.");
    }
}
