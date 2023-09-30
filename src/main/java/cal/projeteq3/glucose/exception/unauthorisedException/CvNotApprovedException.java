package cal.projeteq3.glucose.exception.unauthorisedException;

import cal.projeteq3.glucose.exception.unauthorizedException.UnauthorizedException;

public class CvNotApprovedException extends UnauthorizedException {
	public CvNotApprovedException(){
		super("Student must have an approved CV");
	}

}
