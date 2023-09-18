package cal.projeteq3.glucose.model.jobOffer;

import cal.projeteq3.glucose.model.user.Employer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobOffer{
	@Id
	@GeneratedValue
	private Long id;

	private JobOfferState jobOfferState = JobOfferState.SUBMITTED;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String department;

	@Column(nullable = false)
	private String location;

	@Column(nullable = false)
	private String description;

	private float salary;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private LocalDateTime startDate;

	private int duration;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private LocalDateTime expirationDate;

	@Column(nullable = false)
	private JobOfferState offerState;

	private int hoursPerWeek;

	@ManyToOne(fetch = FetchType.LAZY)//TODO check cascade on delete jobOffer
	private Employer employer;

	@OneToMany(mappedBy = "jobOffer")
	private List<JobApplication> jobApplications = new ArrayList<>();

	public void copy(JobOffer jobOffer){
		this.title = jobOffer.getTitle();
		this.department = jobOffer.getDepartment();
		this.location = jobOffer.getLocation();
		this.description = jobOffer.getDescription();
		this.salary = jobOffer.getSalary();
		this.startDate = jobOffer.getStartDate();
		this.duration = jobOffer.getDuration();
		this.expirationDate = jobOffer.getExpirationDate();
		this.offerState = jobOffer.getOfferState();
		this.hoursPerWeek = jobOffer.getHoursPerWeek();
	}


}

