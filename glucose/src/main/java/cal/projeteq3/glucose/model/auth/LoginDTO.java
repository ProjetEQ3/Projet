package cal.projeteq3.glucose.model.auth;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO{
	private String email;
	private String password;
}
