package cal.projeteq3.glucose.model.user;

import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.auth.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "_user") // Obligation ici de mettre un nom puisque la table user est déjà utilisé par la BD
public abstract class User{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String firstName;

	private String lastName;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName = "id", nullable = false, unique = true)
	@Getter(AccessLevel.NONE)
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

	public Collection<? extends GrantedAuthority> getAuthorities(){
		return this.credentials.getAuthorities();
	}
}
