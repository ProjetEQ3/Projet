package cal.projeteq3.glucose.model.jobOffer;

import cal.projeteq3.glucose.exception.badRequestException.StudentNotFoundException;
import cal.projeteq3.glucose.model.Appointment;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.user.Student;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication{
	@Id
	@GeneratedValue
	private Long id;

	@Enumerated(EnumType.STRING)
	private JobApplicationState jobApplicationState = JobApplicationState.SUBMITTED;

	@ManyToOne
	private Student student;

	@ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private JobOffer jobOffer;

	@Embedded
	private Semester semester;

	@OneToMany(mappedBy = "jobApplication", cascade = CascadeType.ALL)
	private List<Appointment> appointments = new ArrayList<>();

	public Student getStudent(){
		if(this.student == null) throw new StudentNotFoundException();
		return this.student;
	}

	public void addAppointment(Appointment appointment){
		this.appointments.add(appointment);
	}

	public boolean isNotChangeble() {
		return this.jobApplicationState == JobApplicationState.ACCEPTED || this.jobApplicationState == JobApplicationState.REJECTED;
	}
}
