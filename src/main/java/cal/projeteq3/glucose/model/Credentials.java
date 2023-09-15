package cal.projeteq3.glucose.model;

import cal.projeteq3.glucose.validation.Validation;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "credentials")
public class Credentials<U extends User<?>>{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "email", unique = true, nullable = false, length = 50)
	private String email;

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 20)
	private Role role;

	@OneToOne(mappedBy = "credentials")
	private U user;

	@Builder
	public Credentials(String email, String password, Role role){
		setEmail(email);
		setPassword(password);
		setRole(role);
	}

	public void setEmail(String email){
		Validation.validateEmail(email);
		this.email = email;
	}

	public void setPassword(String password){
		// Validation in Service, here password is already hashed
		this.password = password;
	}

	public void setRole(Role role){
		this.role = role;
	}

}

