package cal.projeteq3.glucose.model.user;

import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.auth.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Manager extends User{
	@Column(unique = true, nullable = false)
	private String matricule;

	private String phoneNumber;

	@Builder
	public Manager(
		Long id, String firstName, String lastName, String email, String password,
		String matricule, String phoneNumber
	){
		super(id, firstName, lastName, Credentials.builder().email(email).password(password).role(Role.MANAGER).build());
		this.matricule = matricule;
		this.phoneNumber = phoneNumber;
	}

}
