package cal.projeteq3.glucose.dto;

import cal.projeteq3.glucose.model.Employer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDTO extends UserDTO {
    private String organisationName;
    private String organisationPhone;

    public EmployerDTO(Employer employer){
        super(employer.getUserID(), employer.getFirstName(), employer.getLastName(), employer.getEmail());
        this.organisationName = employer.getOrganisationName();
        this.organisationPhone = employer.getOrganisationPhone();
    }
}
