package cal.projeteq3.glucose.dto;

import cal.projeteq3.glucose.model.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO extends UserDTO {
    private String matricule;
    private Department department;
}
