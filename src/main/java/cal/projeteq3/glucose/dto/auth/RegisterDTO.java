package cal.projeteq3.glucose.dto.auth;

import cal.projeteq3.glucose.validation.Validation;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDTO{
	private String email;
	private String password;
	private String role;

	public RegisterDTO(String email, String password, String role){
		this.email = email;
		this.password = password;
		this.role = role;
		validate();
	}

	public void validate(){
		Validation.validateEmail(this.email);
		Validation.validatePassword(this.password);
		Validation.validateRole(this.role);
	}

}
