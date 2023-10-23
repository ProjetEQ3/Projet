package cal.projeteq3.glucose.model.jobOffer;

import cal.projeteq3.glucose.exception.badRequestException.StudentNotFoundException;
import cal.projeteq3.glucose.model.Appointment;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.user.Student;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(mappedBy = "jobApplication", cascade = CascadeType.ALL)
	private List<Appointment> appointment = new ArrayList<>();
	public Student getStudent(){
		if(this.student == null) throw new StudentNotFoundException();
		return this.student;
	}

	public void addAppointment(Appointment appointment){
		this.appointment.add(appointment);
	}

}
