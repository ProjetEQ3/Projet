package cal.projeteq3.glucose.exception.badRequestException;

public class CvFileExistException extends BadRequestException{
	public CvFileExistException(){
		super("existingCv");
	}
}
