package cal.projeteq3.glucose.dto;

import cal.projeteq3.glucose.exception.badRequestException.ValidationException;
import cal.projeteq3.glucose.validation.Validation;

public class SerialDto extends ErrorDto{
	private String serial;

	public SerialDto(){
		super();
	}

	public SerialDto(String serial){
		super();
		set(serial);
		validate();
	}

	public String get(){
		return serial;
	}

	public void set(String serial){
		this.serial = serial;
	}

	@Override
	public void validate(){
		clearErrors();
		try{
			Validation.validateSerial(serial);
		}catch(ValidationException e){
			addError(e.getMessage());
		}
	}

}
