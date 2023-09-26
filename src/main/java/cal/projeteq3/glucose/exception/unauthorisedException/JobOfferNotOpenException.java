package cal.projeteq3.glucose.exception.unauthorisedException;

public class JobOfferNotOpenException extends UnauthorisedException{
	public JobOfferNotOpenException(){
		super("Job offer is not open");
	}

}
