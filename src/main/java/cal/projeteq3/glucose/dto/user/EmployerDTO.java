package cal.projeteq3.glucose.dto.user;

import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.model.user.Employer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDTO extends UserDTO {
    private String organisationName;
    private String organisationPhone;
    private List<JobOfferDTO> jobOffers = new ArrayList<>();

    public EmployerDTO(Long id, String firstName, String lastName, String email, String organisationName, String organisationPhone) {
        super(id, firstName, lastName, email, "EMPLOYER");
        this.organisationName = organisationName;
        this.organisationPhone = organisationPhone;
    }

    public EmployerDTO(Employer employer){
        super(
          employer.getId(), employer.getFirstName(), employer.getLastName(),
          employer.getEmail(), employer.getRole().toString()
        );
        this.organisationName = employer.getOrganisationName();
        this.organisationPhone = employer.getOrganisationPhone();
        this.jobOffers = employer.getJobOffers() == null ? null : employer.getJobOffers().stream().map(JobOfferDTO::new).toList();
    }

    public Employer toEntity() {
        return Employer.builder()
                .id(this.getId())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .email(this.getEmail())
                .organisationName(this.getOrganisationName())
                .organisationPhone(this.getOrganisationPhone())
                .build();
    }
}
