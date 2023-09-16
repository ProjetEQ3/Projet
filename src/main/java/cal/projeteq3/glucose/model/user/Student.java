package cal.projeteq3.glucose.model.user;

import cal.projeteq3.glucose.model.Department;
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
	private CvFile cv;
	private String matricule;
	private Department department;

	public Student(
		String firstName, String lastName, String email, String password,
		String matricule, String department, CvFile cvFile
	){
		super(firstName, lastName, email, password);
		this.matricule = matricule;
		this.department = Department.valueOf(department);
		addCv(cvFile);
	}

	@Builder
	public Student(
		Long id, String firstName, String lastName, String email,
		String password, String matricule, String department, CvFile cvFile
	){
		super(id, firstName, lastName, email, password);
		this.matricule = matricule;
		this.department = Department.valueOf(department);
		addCv(cvFile);
	}

	public void addCv(CvFile cvFile){
		this.cv = cvFile;
		if(cvFile != null) this.cv.setStudent(this);
	}

}
