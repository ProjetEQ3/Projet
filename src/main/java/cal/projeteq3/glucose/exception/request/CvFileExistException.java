package cal.projeteq3.glucose.exception.request;

public class CvFileExistException extends BadRequestException{
	public CvFileExistException(){
		super("CcFile already exists");
	}
}
