package cal.projeteq3.glucose.model;

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

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "department", nullable = false)
	private String department;

	@Column(name = "location", nullable = false)
	private String location;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "salary")
	private float salary;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", nullable = false)
	private LocalDateTime startDate;

	@Column(name = "duration")
	private int duration;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expiration_date", nullable = false)
	private LocalDateTime expirationDate;

	@Column(name = "offre_tate", nullable = false)
	private String offreState;

	@Column(name = "hours_per_week")
	private int hoursPerWeek;

	@ManyToOne(fetch = FetchType.LAZY)//TODO check cascade on delete jobOffre
	@JoinColumn(name = "employer")
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

