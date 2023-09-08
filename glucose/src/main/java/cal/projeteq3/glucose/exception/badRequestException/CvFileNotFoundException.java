package cal.projeteq3.glucose.exception.badRequestException;

public class CvFileNotFoundException extends BadRequestException{
	public CvFileNotFoundException(){
		super("CvFile not found");
	}
}

