package cal.projeteq3.glucose.exception.unauthorizedException;

import cal.projeteq3.glucose.exception.request.BadRequestException;

public class JobApplicationNotFoundException extends BadRequestException{
	public JobApplicationNotFoundException(){
		super("La candidature n'existe pas");
	}

}
