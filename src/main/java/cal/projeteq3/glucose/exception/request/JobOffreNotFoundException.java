package cal.projeteq3.glucose.exception.request;

public class JobOffreNotFoundException extends BadRequestException{

	public JobOffreNotFoundException(Long id) {
		super("Job offer with id " + id + " not found");
	}

}
