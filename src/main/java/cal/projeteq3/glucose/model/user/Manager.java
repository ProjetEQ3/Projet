package cal.projeteq3.glucose.model.user;

import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.auth.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Manager extends User{
	@Column(unique = true, nullable = false)
	private String matricule;
	private String phoneNumber;
	private Department department;

	@Builder
	public Manager(
		Long id, String firstName, String lastName, String email, String password,
		String matricule, String phoneNumber, Department department
	){
		super(id, firstName, lastName, Credentials.builder().email(email).password(password).role(Role.MANAGER).build());
		this.matricule = matricule;
		this.phoneNumber = phoneNumber;
		this.department = department;
	}
}
