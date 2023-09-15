package cal.projeteq3.glucose.dto;

import cal.projeteq3.glucose.model.JobOffer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOfferDTO{
	private Long id;
	private String state;
	private String title;
	private String description;
	private String location;
	private String department;
	private float salary;
	private String startDate;
	private int duration;
	private int hoursPerWeek;
	private String expirationDate;


	public JobOfferDTO(JobOffer jobOffer){
		this.id = jobOffer.getId();
		this.state = jobOffer.getState();
		this.title = jobOffer.getTitle();
		this.description = jobOffer.getDescription();
		this.location = jobOffer.getLocation();
		this.department = jobOffer.getDepartment();
		this.salary = jobOffer.getSalary();
		this.startDate = jobOffer.getStartDate().toString();
		this.duration = jobOffer.getDuration();
		this.hoursPerWeek = jobOffer.getHoursPerWeek();
		this.expirationDate = jobOffer.getExpirationDate().toString();
	}
}
