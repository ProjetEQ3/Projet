package cal.projeteq3.glucose.exception.request;

public class CvFileNotFoundException extends BadRequestException{
	public CvFileNotFoundException(){
		super("cvFileNotFound");
	}
}

