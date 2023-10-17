package cal.projeteq3.glucose.model.jobOffer;

import cal.projeteq3.glucose.exception.badRequestException.StudentNotFoundException;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.user.Student;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication{
	@Id
	@GeneratedValue
	private Long id;

	private JobApplicationState jobApplicationState = JobApplicationState.SUBMITTED;

	@ManyToOne
	private Student student;

	@ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private JobOffer jobOffer;

	@Embedded
	private Semester semester;

	public Student getStudent(){
		if(this.student == null) throw new StudentNotFoundException();
		return this.student;
	}

}
