package cal.projeteq3.glucose.Dto.auth;

import cal.projeteq3.glucose.validation.Validation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto{
	private String email;
	private String password;

	public LoginDto(String email, String password){
		setEmail(email);
		setPassword(password);
		validate();
	}

	public void validate(){
		Validation.validateEmail(email);
		Validation.validatePassword(password);
	}

}
