package cal.projeteq3.glucose.exception.unauthorisedException;

import cal.projeteq3.glucose.exception.unauthorizedException.UnauthorizedException;

public class StudentHasAlreadyAppliedException extends UnauthorizedException {
	public StudentHasAlreadyAppliedException(){
		super("Student has already applied to this job offer");
	}

}
