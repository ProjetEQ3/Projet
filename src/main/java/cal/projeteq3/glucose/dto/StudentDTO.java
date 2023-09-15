package cal.projeteq3.glucose.dto;

import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Student;
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

    public StudentDTO(Student student){
        super(student.getId(), student.getFirstName(), student.getLastName(), student.getEmail(), student.getRole().toString());
        this.matricule = student.getMatricule();
        this.department = student.getDepartment();
    }

}
