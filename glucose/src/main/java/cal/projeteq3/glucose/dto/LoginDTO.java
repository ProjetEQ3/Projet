package cal.projeteq3.glucose.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO{
	private String email;
	private String password;
}
