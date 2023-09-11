package cal.projeteq3.glucose.dto;

import cal.projeteq3.glucose.model.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO extends UserDTO {
    private String matricule;
    private Department department;
}
