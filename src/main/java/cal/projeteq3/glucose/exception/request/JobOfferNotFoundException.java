package cal.projeteq3.glucose.exception.request;

public class JobOfferNotFoundException extends BadRequestException{
	public JobOfferNotFoundException(){
		super("Job offer not found");
	}

}
