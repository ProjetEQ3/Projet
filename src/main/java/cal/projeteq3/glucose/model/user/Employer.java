package cal.projeteq3.glucose.model.user;

import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.auth.Role;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
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

	private String organisationName;

	private String organisationPhone;

	@OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
	private List<JobOffer> jobOffers = new ArrayList<>();

	@Builder
	public Employer(
				Long id, String firstName, String lastName, String email, String password,
				String organisationName, String organisationPhone, List<JobOffer> jobOffers
		){
			super(id, firstName, lastName, Credentials.builder().email(email).password(password).role(Role.EMPLOYER).build());
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
