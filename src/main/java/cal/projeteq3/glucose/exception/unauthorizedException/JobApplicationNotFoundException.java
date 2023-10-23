package cal.projeteq3.glucose.exception.unauthorizedException;

import cal.projeteq3.glucose.exception.badRequestException.BadRequestException;

public class JobApplicationNotFoundException extends BadRequestException{
	public JobApplicationNotFoundException(){
		super("jobApplicationNotFound");
	}

}
