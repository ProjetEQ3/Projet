package cal.projeteq3.glucose.exception.unauthorizedException;

public class StudentHasAlreadyCVException extends UnauthorizedException{
	public StudentHasAlreadyCVException(){
		super("L'étudiant a déjà un CV");
	}

}
