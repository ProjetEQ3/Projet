package cal.projeteq3.glucose.model.auth;

import cal.projeteq3.glucose.model.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credentials")
public final class Credentials{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 20)
	private Role role;

}
