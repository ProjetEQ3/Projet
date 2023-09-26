package cal.projeteq3.glucose.exception.unauthorisedException;

public class CvNotApprovedException extends UnauthorisedException{
	public CvNotApprovedException(){
		super("Student must have an approved CV");
	}

}
