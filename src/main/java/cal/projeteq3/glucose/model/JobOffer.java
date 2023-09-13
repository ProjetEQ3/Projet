package cal.projeteq3.glucose.model;

import cal.projeteq3.glucose.dto.JobOfferDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOffer {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employer")
	private Employer employer;

	@OneToMany(mappedBy = "jobOffer")
	private List<JobApplication> jobApplications;

	private String state;
	private String title;
	private String department;
	private String description;
	private String location;
	private float salary;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime startDate;
	private int duration;
	private int hoursPerWeek;
	private LocalDateTime expirationDate;


	public JobOffer(String title, String description, String location, LocalDateTime startDate, LocalDateTime endDate, int hoursPerWeek, LocalDateTime expirationDate, float salary, int duration) {
		this.state = "pending";
		this.jobApplications = new ArrayList<>();
		this.title = title;
		this.description = description;
		this.location = location;
		this.startDate = startDate;
		this.hoursPerWeek = hoursPerWeek;
		this.expirationDate = expirationDate;
		this.salary = salary;
		this.duration = duration;
	}

//	public JobOfferDTO toDTO() {
//		return new JobOfferDTO(
//				this.id,
//				this.employer.getUserID(),
//				this.state,
//				this.title,
//				this.department,
//				this.description,
//				this.location,
//				this.salary,
//				this.startDate,
//				this.duration,
//				this.hoursPerWeek,
//				this.expirationDate
//		);
//	}
}

