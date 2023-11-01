package cal.projeteq3.glucose.exception.badRequestException;

public class StudentNotFoundException extends BadRequestException{

//	TODO: add id to the message
	public StudentNotFoundException(Long id){
		super("studentNotFound");
	}

	public StudentNotFoundException(){
		super("studentNotFound");
	}

}
