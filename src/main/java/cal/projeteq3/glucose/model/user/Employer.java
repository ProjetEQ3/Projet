package cal.projeteq3.glucose.model.user;

import cal.projeteq3.glucose.model.auth.Credential;
import cal.projeteq3.glucose.model.auth.Role;
import cal.projeteq3.glucose.model.jobOffre.JobOffer;
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

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "credential", referencedColumnName = "id", nullable = false)
	private Credential credentials = new Credential();

	@OneToMany(mappedBy = "employer")//TODO check cascade on delete jobOffre
	private List<JobOffer> jobOffers = new ArrayList<>();

	@Builder
	public Employer(
		Long id, String firstName, String lastName, String email, String password,
		String organisationName, String organisationPhone, List<JobOffer> jobOffers
	){
		super(id, firstName, lastName, Credential.builder().email(email).password(password).role(Role.EMPLOYER).build());
		this.organisationName = organisationName;
		this.organisationPhone = organisationPhone;
		if(jobOffers != null)
			jobOffers.forEach(this::addJobOffer);
	}

	public void addJobOffer(JobOffer jobOffer){
		jobOffer.setEmployer(this);
		this.jobOffers.add(jobOffer);
	}

}
