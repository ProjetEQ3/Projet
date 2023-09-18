package cal.projeteq3.glucose.dto.auth;

import cal.projeteq3.glucose.dto.user.StudentDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterStudentDTO {

    private RegisterDTO registerDTO;
    private StudentDTO studentDTO;

    public RegisterStudentDTO(RegisterDTO registerDTO, StudentDTO studentDTO) {
        this.registerDTO = registerDTO;
        this.studentDTO = studentDTO;
    }
}
