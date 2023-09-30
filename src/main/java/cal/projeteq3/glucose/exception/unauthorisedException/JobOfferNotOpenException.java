package cal.projeteq3.glucose.exception.unauthorisedException;

import cal.projeteq3.glucose.exception.unauthorizedException.UnauthorizedException;

public class JobOfferNotOpenException extends UnauthorizedException {
	public JobOfferNotOpenException(){
		super("Job offer is not open");
	}

}
