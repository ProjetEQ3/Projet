package cal.projeteq3.glucose.model.jobOffre;

import cal.projeteq3.glucose.model.JobApplication;
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
	private String offreState;

	private int hoursPerWeek;

	@ManyToOne(fetch = FetchType.LAZY)//TODO check cascade on delete jobOffre
	private Employer employer;

	@OneToMany(mappedBy = "jobOffer")
	private List<JobApplication> jobApplications = new ArrayList<>();
	//TODO: c quoi ca ??

	public void copy(JobOffer jobOffer){
		this.title = jobOffer.getTitle();
		this.department = jobOffer.getDepartment();
		this.location = jobOffer.getLocation();
		this.description = jobOffer.getDescription();
		this.salary = jobOffer.getSalary();
		this.startDate = jobOffer.getStartDate();
		this.duration = jobOffer.getDuration();
		this.expirationDate = jobOffer.getExpirationDate();
		this.offreState = jobOffer.getOffreState();
		this.hoursPerWeek = jobOffer.getHoursPerWeek();
	}

	/*public JobOffer(String title, String description, String location, LocalDateTime startDate,
		LocalDateTime endDate, int hoursPerWeek, LocalDateTime expirationDate, float salary, int duration) {
		this.offreState = "pending";
		this.jobApplications = new ArrayList<>();
		this.title = title;
		this.description = description;
		this.location = location;
		this.startDate = startDate;
		this.hoursPerWeek = hoursPerWeek;
		this.expirationDate = expirationDate;
		this.salary = salary;
		this.duration = duration;
	}*/

	/*public JobOfferDTO toDTO() {
		return new JobOfferDTO(
				this.id,
				this.employer.getUserID(),
				this.state,
				this.title,
				this.department,
				this.description,
				this.location,
				this.salary,
				this.startDate,
				this.duration,
				this.hoursPerWeek,
				this.expirationDate
		);
	}*/

}

