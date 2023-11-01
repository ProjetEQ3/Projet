package cal.projeteq3.glucose.exception.badRequestException;

public class JobOfferNotFoundException extends BadRequestException{

	//    TODO: add id to the message
	public JobOfferNotFoundException(Long id) {
		super("jobOfferNotFound");
	}

	public JobOfferNotFoundException() {
		super("jobOfferNotFound");
	}
}
