package cal.projeteq3.glucose.dto.auth;

import cal.projeteq3.glucose.dto.user.UserDTO;
import cal.projeteq3.glucose.validation.Validation;
import lombok.*;

@Data
@NoArgsConstructor
public class RegisterDTO{
	private String email;
	private String password;
	private String role;

	public RegisterDTO(String email, String password, String role){
		this.email = email;
		this.password = password;
		this.role = role;
	}

}
