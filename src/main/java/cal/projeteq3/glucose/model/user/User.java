package cal.projeteq3.glucose.model.user;

import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.auth.Role;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class User{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "credentials", referencedColumnName = "id", nullable = false)
	private Credentials credentials;

	public User(String lastName, String firstName, Credentials credentials){
		this.lastName = lastName;
		this.firstName = firstName;
		this.credentials = credentials;
	}

	public String getEmail(){
		return this.credentials.getEmail();
	}

	public String getPassword(){
		return this.credentials.getPassword();
	}

	public Role getRole(){
		return this.credentials.getRole();
	}

	public void setEmail(String email){
		this.credentials.setEmail(email);
	}

	public void setPassword(String password){
		this.credentials.setPassword(password);
	}

	public void setRole(Role role){
		this.credentials.setRole(role);
	}

}