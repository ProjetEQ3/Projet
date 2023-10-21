package cal.projeteq3.glucose.dto.jobOffer;

import cal.projeteq3.glucose.dto.AppointmentDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.model.jobOffer.JobApplicationState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.LifecycleState;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class JobApplicationDTO{
	private Long id;
	private StudentDTO student;
	private JobOfferDTO jobOffer;
	private JobApplicationState jobApplicationState;
	private Semester semester;

	public JobApplicationDTO(JobApplication jobApplication){
		this.id = jobApplication.getId();
		this.student = new StudentDTO(jobApplication.getStudent());
		this.jobOffer = new JobOfferDTO(jobApplication.getJobOffer());
		this.jobApplicationState = jobApplication.getJobApplicationState();
		this.semester = jobApplication.getSemester();
	}

	public JobApplication toEntity(){
		return JobApplication.builder()
				.id(id)
				.student(student.toEntity())
				.jobOffer(jobOffer.toEntity())
				.jobApplicationState(jobApplicationState)
				.semester(semester)
				.build();
	}

}
