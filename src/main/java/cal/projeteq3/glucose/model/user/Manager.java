package cal.projeteq3.glucose.model.user;

import cal.projeteq3.glucose.model.auth.Credential;
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
	@Column(name = "matricule")
	private String matricule;

	@Column(name = "phone_number")
	private String phoneNumber;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "credential", referencedColumnName = "id", nullable = false)
	private Credential credentials = new Credential();

	@Builder
	public Manager(
		Long id, String firstName, String lastName, String email, String password,
		String matricule, String phoneNumber
	){
		super(id, firstName, lastName, Credential.builder().email(email).password(password).role(Role.MANAGER).build());
		this.matricule = matricule;
		this.phoneNumber = phoneNumber;
	}

}
