package cal.projeteq3.glucose.model;

import cal.projeteq3.glucose.dto.EmployerDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Employer extends User {
    private String organisationName;
    private String organisationPhone;
    @OneToMany(mappedBy = "employer")
    private List<JobOffer> jobOffer;

    public Employer(String firstName, String lastName, String email, String password, String organisationName, String organisationPhone, List<JobOffer> jobOffer) {
        super(firstName, lastName, email, password);
        this.organisationName = organisationName;
        this.organisationPhone = organisationPhone;
        this.jobOffer = jobOffer;
    }

    public EmployerDTO toDTO() {
        EmployerDTO employerDTO = new EmployerDTO();

        employerDTO.setId(this.getUserID());
        employerDTO.setLastName(this.getLastName());
        employerDTO.setFirstName(this.getFirstName());
        employerDTO.setEmail(this.getEmail());
        employerDTO.setOrganisationName(this.getOrganisationName());
        employerDTO.setOrganisationPhone(this.getOrganisationPhone());

        return employerDTO;
    }

}
