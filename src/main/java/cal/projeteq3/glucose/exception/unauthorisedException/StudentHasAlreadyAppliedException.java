package cal.projeteq3.glucose.exception.unauthorisedException;

public class StudentHasAlreadyAppliedException extends UnauthorisedException{
	public StudentHasAlreadyAppliedException(){
		super("Student has already applied to this job offer");
	}

}
