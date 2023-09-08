package cal.projeteq3.glucose.auth;

import cal.projeteq3.glucose.validation.Validation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto{
	private String email;
	private String password;

	@JsonCreator
	public LoginDto(
		@JsonProperty("email") String email,
		@JsonProperty("password") String password){
		setEmail(email);
		setPassword(password);
		validate();
	}

	public void validate(){
		Validation.validateEmail(email);
		Validation.validatePassword(password);
	}

}
