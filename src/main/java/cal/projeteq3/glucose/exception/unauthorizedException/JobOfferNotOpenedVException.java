package cal.projeteq3.glucose.exception.unauthorizedException;

public class JobOfferNotOpenedVException extends UnauthorizedException{
	public JobOfferNotOpenedVException(){
		super("L'offre d'emploi n'est pas ouverte");
	}

}
