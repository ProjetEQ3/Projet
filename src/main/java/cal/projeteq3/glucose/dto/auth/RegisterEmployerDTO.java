package cal.projeteq3.glucose.dto.auth;

import cal.projeteq3.glucose.dto.user.EmployerDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterEmployerDTO {
    private RegisterDTO registerDTO;
    private EmployerDTO employerDTO;

    public RegisterEmployerDTO(RegisterDTO registerDTO, EmployerDTO employerDTO) {
        this.registerDTO = registerDTO;
        this.employerDTO = employerDTO;
    }
}
