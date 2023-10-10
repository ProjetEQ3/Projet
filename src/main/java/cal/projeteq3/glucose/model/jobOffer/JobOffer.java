package cal.projeteq3.glucose.model.jobOffer;

import cal.projeteq3.glucose.exception.unauthorizedException.CvNotApprovedException;
import cal.projeteq3.glucose.exception.unauthorizedException.JobOfferNotOpenException;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
	@Enumerated(EnumType.STRING)
	private JobOfferState jobOfferState = JobOfferState.SUBMITTED;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private Department department;

	@Column(nullable = false)
	private String location;

	@Column(nullable = false, length = 2000)
	private String description;

	private float salary;

	@Column(nullable = false)
	private LocalDate startDate;

	private int duration;

	@Column(nullable = false)
	private LocalDate expirationDate;

	private int hoursPerWeek;

	private int nbOfCandidates;

	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)//TODO check cascade on delete jobOffer
	private Employer employer;

	@OneToMany(mappedBy = "jobOffer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<JobApplication> jobApplications = new ArrayList<>();

	private Long acceptedJobApplicationId;

	private String refusReason;

	public void copy(JobOffer jobOffer){
		this.title = jobOffer.getTitle();
		this.department = jobOffer.getDepartment();
		this.location = jobOffer.getLocation();
		this.description = jobOffer.getDescription();
		this.salary = jobOffer.getSalary();
		this.startDate = jobOffer.getStartDate();
		this.duration = jobOffer.getDuration();
		this.expirationDate = jobOffer.getExpirationDate();
		this.jobOfferState = jobOffer.getJobOfferState();
		this.hoursPerWeek = jobOffer.getHoursPerWeek();
		this.nbOfCandidates = jobOffer.getNbOfCandidates();
	}

	// EQ3-13
	public JobApplication apply(Student student){
		if(!student.hasApprovedCv()) throw new CvNotApprovedException();
		if(getJobOfferState() != JobOfferState.OPEN) throw new JobOfferNotOpenException();

		JobApplication jobApplication = new JobApplication();
		jobApplication.setStudent(student);
		jobApplication.setJobOffer(this);
		this.jobApplications.add(jobApplication);

		return jobApplication;
	}

	public boolean hasApplied(Long studentId){
		return this.jobApplications.stream().anyMatch(jobApplication -> jobApplication.getStudent().getId().equals(studentId));
	}

}
