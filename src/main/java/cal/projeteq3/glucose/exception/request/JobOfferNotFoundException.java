package cal.projeteq3.glucose.exception.request;

public class JobOfferNotFoundException extends BadRequestException{
	public JobOfferNotFoundException(Long id) {
		super("Job offer with id " + id + " not found");
	}

	public JobOfferNotFoundException() {
		super("Job offer with title not found");
	}
}
