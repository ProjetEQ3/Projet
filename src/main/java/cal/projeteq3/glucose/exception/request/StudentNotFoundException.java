package cal.projeteq3.glucose.exception.request;

public class StudentNotFoundException extends BadRequestException{

	public StudentNotFoundException(Long id){
		super("Student with ID " + id + " does not exist.");
	}

	public StudentNotFoundException(){
		super("Student does not exist.");
	}

    public StudentNotFoundException(){
        super("L'étudiant n'existe pas");
    }
}
