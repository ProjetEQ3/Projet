package cal.projeteq3.glucose.exception.badRequestException;

public class StudentNotFoundException extends BadRequestException{

	public StudentNotFoundException(Long id){
		super("Student with ID " + id + " does not exist.");
	}

	public StudentNotFoundException(){
		super("Student does not exist.");
	}

}
