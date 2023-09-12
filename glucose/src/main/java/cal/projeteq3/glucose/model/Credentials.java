package cal.projeteq3.glucose.model;

import cal.projeteq3.glucose.validation.Validation;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Embeddable
public class Credentials{
	@Column(name = "email", unique = true, nullable = false, length = 50)
	private String email;

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 20)
	private Role role;

	public Credentials(){}

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
		//ValidationUtils.validatePassword(password);TODO validate in service
		this.password = password;
	}

	public void setRole(Role role){
		this.role = role;
	}

}
