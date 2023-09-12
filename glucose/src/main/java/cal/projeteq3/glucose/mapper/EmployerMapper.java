package cal.projeteq3.glucose.mapper;

import cal.projeteq3.glucose.dto.EmployerDTO;
import cal.projeteq3.glucose.model.Employer;
import org.springframework.stereotype.Component;

@Component
public class EmployerMapper {

    public EmployerDTO toDTO(Employer employer) {
        EmployerDTO employerDTO = new EmployerDTO();

        employerDTO.setId(employer.getUserID());
        employerDTO.setLastName(employer.getLastName());
        employerDTO.setFirstName(employer.getFirstName());
        employerDTO.setEmail(employer.getEmail());
        employerDTO.setOrganisationName(employer.getOrganisationName());
        employerDTO.setOrganisationPhone(employer.getOrganisationPhone());

        return employerDTO;
    }

    public Employer toEntity(EmployerDTO employerDTO) {
        Employer employer = new Employer();

        employer.setUserID(employerDTO.getId());
        employer.setLastName(employerDTO.getLastName());
        employer.setFirstName(employerDTO.getFirstName());
        employer.setEmail(employerDTO.getEmail());
        employer.setOrganisationName(employerDTO.getOrganisationName());
        employer.setOrganisationPhone(employerDTO.getOrganisationPhone());

        return employer;
    }
}
