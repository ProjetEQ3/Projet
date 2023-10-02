package cal.projeteq3.glucose.model.jobOffer;

import cal.projeteq3.glucose.exception.request.StudentNotFoundException;
import cal.projeteq3.glucose.exception.unauthorizedException.JobOfferNotOpenedVException;
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

	boolean refused = false;
	String refusReason;

	@ManyToOne
	private Student student;

	@ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private JobOffer jobOffer;

	public void acceptApplication(){
		if(this.jobOffer.getJobOfferState() != JobOfferState.OPEN)
			throw new JobOfferNotOpenedVException();
		this.jobOffer.setJobOfferState(JobOfferState.TAKEN);
		this.jobOffer.setAcceptedJobApplicationId(this.id);
	}

	public void refuseApplication(String reason){
		if(this.jobOffer.getJobOfferState() != JobOfferState.OPEN)
			throw new JobOfferNotOpenedVException();
		setRefused(true);
		setRefusReason(reason);
	}

	public Student getStudent(){
		if(this.student == null) throw new StudentNotFoundException();
		return this.student;
	}

}
