package cal.projeteq3.glucose.dto.jobOffer;

import cal.projeteq3.glucose.dto.AppointmentDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.model.jobOffer.JobApplicationState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public final class JobApplicationDTO{
	private Long id;
	private StudentDTO student;
	private JobOfferDTO jobOffer;
	private JobApplicationState jobApplicationState;
	private Semester semester;
	private List<AppointmentDTO> appointments;

	public JobApplicationDTO(JobApplication jobApplication){
		this.id = jobApplication.getId();
		this.student = new StudentDTO(jobApplication.getStudent());
		this.jobOffer = new JobOfferDTO(jobApplication.getJobOffer());
		this.jobApplicationState = jobApplication.getJobApplicationState();
		this.semester = jobApplication.getSemester();
	}
}
