package cal.projeteq3.glucose.model.user;

import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.auth.Role;
import cal.projeteq3.glucose.model.cvFile.CvFile;
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User{
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private CvFile cvFile;

	@Column(unique = true, nullable = false)
	private String matricule;

	@Enumerated(EnumType.STRING)
	private Department department;

	@Builder
	public Student(Long id, String firstName, String lastName, String email, String password, String matricule, String department, CvFile cvFile){
		super(id, firstName, lastName, Credentials.builder().email(email).password(password).role(Role.STUDENT).build());
		this.matricule = matricule;
		if(department != null) this.department = Department.valueOf(department);
		setCvFile(cvFile);
	}

	public Student(String lastName, String firstName, String email, String password, String matricule, String department){
		super(lastName, firstName, Credentials.builder().email(email).password(password).role(Role.STUDENT).build());
		this.matricule = matricule;
		this.department = Department.valueOf(department);
	}

	public void setCvFile(CvFile cvFile){
		this.cvFile = cvFile;
		if(cvFile != null) this.cvFile.setStudent(this);
	}

	public void deleteCv(){
		if(this.cvFile != null){
			this.cvFile.setStudent(null);
			this.cvFile = null;
		}
	}

	public boolean hasApprovedCv(){
		return this.cvFile != null && this.cvFile.getCvState() == CvState.ACCEPTED;
	}
}
