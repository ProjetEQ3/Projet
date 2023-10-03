package cal.projeteq3.glucose.dto.auth;

import cal.projeteq3.glucose.dto.user.SupervisorDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterSupervisorDTO {
    private RegisterDTO registerDTO;
    private SupervisorDTO supervisorDTO;

    public RegisterSupervisorDTO(RegisterDTO registerDTO, SupervisorDTO supervisorDTO) {
        this.registerDTO = registerDTO;
        this.supervisorDTO = supervisorDTO;
    }
}
