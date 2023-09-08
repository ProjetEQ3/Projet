package cal.projeteq3.glucose.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class Model{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Embedded
	private final Serial serial = new Serial();

	public Model(){
		this.serial.generateSerial(getSerialPrefix());
	}

	public Model(String serial){
		if(serial != null) serial = serial.trim();
		if(serial == null || serial.isEmpty() || serial.isBlank() ||
			serial.length() <= getSerialPrefix().length() + Serial.SEPARATOR.length() ||
			!serial.startsWith(getSerialPrefix() + Serial.SEPARATOR)
		){
			this.serial.generateSerial(getSerialPrefix());
		}else if(
			serial.startsWith(getSerialPrefix() + Serial.SEPARATOR) &&
				serial.length() > getSerialPrefix().length() + Serial.SEPARATOR.length()
		){
			setSerial(serial);
		}else this.serial.addPrefix(getSerialPrefix());
	}

	public abstract String getSerialPrefix();

	public String getSerial(){
		return serial.get();
	}

	public void setSerial(String serial){
		this.serial.set(serial);
	}

	public void copy(Model model){}

}
