package cal.projeteq3.glucose.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Employer extends User{

	@Column(name = "organisation_name")
	private String organisationName;

	@Column(name = "organisation_phone")
	private String organisationPhone;

	@OneToMany(mappedBy = "employer")//TODO check cascade on delete jobOffre
	private List<JobOffer> jobOffers = new ArrayList<>();

	@Builder
	public Employer(long id, String firstName, String lastName, String email, String password, String organisationName, String organisationPhone, List<JobOffer> jobOffers){
		super(id, firstName, lastName, email, password);
		this.organisationName = organisationName;
		this.organisationPhone = organisationPhone;
		jobOffers.forEach(this::addJobOffer);
	}

	/*@Builder
	public Employer(long id, String firstName, String lastName, String email, String password, String organisationName, String organisationPhone, List<JobOffer> jobOffers){
		super(id, firstName, lastName, email, password);
		this.organisationName = organisationName;
		this.organisationPhone = organisationPhone;
		this.jobOffers = jobOffers;
	}*/

	/*public Employer(String firstName, String lastName, String email, String password, String organisationName, String organisationPhone, List<JobOffer> jobOffers){
		super(firstName, lastName, email, password);
		this.organisationName = organisationName;
		this.organisationPhone = organisationPhone;
		this.jobOffers = jobOffers;
	}*/

	public void addJobOffer(JobOffer jobOffer){
		jobOffer.setEmployer(this);
		this.jobOffers.add(jobOffer);
	}

}
