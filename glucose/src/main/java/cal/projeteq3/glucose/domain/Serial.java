package cal.projeteq3.glucose.domain;

import cal.projeteq3.glucose.validation.Validation;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Setter
@Getter
@Embeddable
public final class Serial{
	public final static String SEPARATOR = "_";
	private final static String DEFAULT_PREFIX = "serial";

	@Column(name = "serial", unique = true, nullable = false, updatable = false)
	private String serial;

	public Serial(){
		//generateSerial(DEFAULT_PREFIX);
	}

	public Serial(String serial){
		set(serial);
	}

	public String get(){
		return serial;
	}

	public void set(String serial){
		Validation.validateSerial(serial);
		this.serial = serial.trim();
	}

	public void generateSerial(String prefix){
		set(UUID.randomUUID().toString());
		addPrefix(prefix);
	}

	public void addPrefix(String prefix){
		if(prefix == null || prefix.isEmpty()) prefix = DEFAULT_PREFIX;
		prefix = prefix.trim();
		set(prefix + SEPARATOR + serial);
	}

	@Override
	public String toString(){
		return get();
	}

}
