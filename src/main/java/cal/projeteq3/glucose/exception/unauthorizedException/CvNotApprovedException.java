package cal.projeteq3.glucose.exception.unauthorizedException;

import cal.projeteq3.glucose.exception.unauthorizedException.UnauthorizedException;

public class CvNotApprovedException extends UnauthorizedException {
	public CvNotApprovedException(){
		super("cvNotApproved");
	}

}
