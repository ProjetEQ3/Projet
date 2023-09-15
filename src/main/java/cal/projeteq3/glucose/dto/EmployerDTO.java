package cal.projeteq3.glucose.dto;

import cal.projeteq3.glucose.model.Employer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDTO extends UserDTO {
    private String organisationName;
    private String organisationPhone;
    private List<JobOfferDTO> jobOffers;

    public EmployerDTO(Employer employer){
        super(employer.getId(), employer.getFirstName(), employer.getLastName(), employer.getEmail(), employer.getRole().toString());
        this.organisationName = employer.getOrganisationName();
        this.organisationPhone = employer.getOrganisationPhone();
        this.jobOffers = employer.getJobOffer() == null ? null : employer.getJobOffer().stream().map(JobOfferDTO::new).toList();
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
