package cal.projeteq3.glucose.model;

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
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime startDate;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime endDate;
	private int hoursPerWeek;
	private int nbDaysToApply;


	public JobOffer(String title, String description, String location, LocalDateTime startDate, LocalDateTime endDate, int hoursPerWeek, int nbDaysToApply) {
		this.state = "pending";
		this.jobApplications = new ArrayList<>();
		this.title = title;
		this.description = description;
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
		this.hoursPerWeek = hoursPerWeek;
		this.nbDaysToApply = nbDaysToApply;
	}
}

