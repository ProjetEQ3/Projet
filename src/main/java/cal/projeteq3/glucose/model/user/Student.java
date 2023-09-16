package cal.projeteq3.glucose.model.user;

import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.auth.Role;
import cal.projeteq3.glucose.model.cvFile.CvFile;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User{
	@OneToOne
	private CvFile cvFile;

	@Column(name = "matricule")
	private String matricule;

	@Column(name = "department")
	private Department department;

	@Builder
	public Student(
		Long id, String firstName, String lastName, String email, String password,
		String matricule, String department, CvFile cvFile
	){
		super(id, firstName, lastName, Credentials.builder().email(email).password(password).role(Role.STUDENT).build());
		this.matricule = matricule;
		this.department = Department.valueOf(department);
		addCv(cvFile);
	}

	public void addCv(CvFile cvFile){
		this.cvFile = cvFile;
		if(cvFile != null) this.cvFile.setStudent(this);
	}

}
