package cal.projeteq3.glucose.dto;

import java.util.ArrayList;
import java.util.List;

public abstract class ErrorDto{
	private List<String> errors;

	public ErrorDto(){
		errors = new ArrayList<>();
	}

	public abstract void validate();

	public List<String> getErrors(){
		List<String> err = new ArrayList<>(errors);
		errors.clear();
		return err;
	}

	public void setErrors(List<String> errors){
		this.errors = errors;
	}

	public boolean hasErrors(){
		return errors != null && !errors.isEmpty();
	}

	public void addError(String error){
		if(errors == null) errors = new ArrayList<>();
		errors.add(error);
	}

	public void addErrors(List<String> errors){
		if(this.errors == null) this.errors = new ArrayList<>();
		this.errors.addAll(errors);
	}

	public void clearErrors(){
		errors = new ArrayList<>();
	}

}
