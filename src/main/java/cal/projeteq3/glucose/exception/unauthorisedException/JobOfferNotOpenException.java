package cal.projeteq3.glucose.exception.unauthorisedException;

public class JobOfferNotOpenException extends UnauthorisedException{
	public JobOfferNotOpenException(){
		super("L'offre d'emploi n'est pas ouverte");
	}

}
