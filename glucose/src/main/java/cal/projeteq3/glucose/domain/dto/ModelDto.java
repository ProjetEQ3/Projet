package cal.projeteq3.glucose.domain.dto;

import cal.projeteq3.glucose.domain.Model;
import cal.projeteq3.glucose.exception.badRequestException.ValidationException;

public abstract class ModelDto extends ErrorDto{
	private final SerialDto serial = new SerialDto();

	public ModelDto(){}

	public ModelDto(String serial){
		setSerial(serial);
	}

	public ModelDto(Model model){
		setSerial(model.getSerial());
	}

	public String getSerial(){
		return serial.get();
	}

	public void setSerial(String serial){
		this.serial.set(serial);
	}

	@Override
	public void validate(){
		clearErrors();
		try{
			serial.validate();
		}catch(ValidationException e){
			addError(e.getMessage());
		}
	}

}
