package cal.projeteq3.glucose.exception.unauthorizedException;

import cal.projeteq3.glucose.exception.unauthorizedException.UnauthorizedException;

public class StudentHasAlreadyAppliedException extends UnauthorizedException {
	public StudentHasAlreadyAppliedException(){
		super("studentAlreadyApplied");
	}

}
