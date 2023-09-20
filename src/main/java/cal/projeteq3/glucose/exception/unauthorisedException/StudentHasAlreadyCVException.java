package cal.projeteq3.glucose.exception.unauthorisedException;

public class StudentHasAlreadyCVException extends UnauthorisedException{
	public StudentHasAlreadyCVException(){
		super("L'étudiant a déjà un CV");
	}

}
