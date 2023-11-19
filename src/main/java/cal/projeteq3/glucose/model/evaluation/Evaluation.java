package cal.projeteq3.glucose.model.evaluation;

import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.model.user.Student;
import jakarta.persistence.*;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Evaluation{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "student_id", referencedColumnName = "id")
	private Student student;

	@ManyToOne
	@JoinColumn(name = "manager_id", referencedColumnName = "id")
	private Manager manager;

	@ManyToOne
	@JoinColumn(name = "employer_id", referencedColumnName = "id")
	private Employer employer;

}
