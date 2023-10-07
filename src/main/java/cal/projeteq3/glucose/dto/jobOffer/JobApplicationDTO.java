package cal.projeteq3.glucose.dto.jobOffer;

import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.model.jobOffer.JobApplicationState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class JobApplicationDTO{
	private Long id;
	private StudentDTO student;
	private JobOfferDTO jobOffer;
	private JobApplicationState jobApplicationState;

	public JobApplicationDTO(JobApplication jobApplication){
		this.id = jobApplication.getId();
		this.student = new StudentDTO(jobApplication.getStudent());
		this.jobOffer = new JobOfferDTO(jobApplication.getJobOffer());
		this.jobApplicationState = jobApplication.getJobApplicationState();
	}

	public JobApplication toEntity(){
		return JobApplication.builder()
				.id(id)
				.student(student.toEntity())
				.jobOffer(jobOffer.toEntity())
				.jobApplicationState(jobApplicationState)
				.build();
	}

}
