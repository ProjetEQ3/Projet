package cal.projeteq3.glucose.dto.auth;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO{
	private String email;
	private String password;
}
