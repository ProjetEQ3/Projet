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

    public StudentDTO(Long id, String firstName, String lastName, String email, String matricule, String department) {
        super(id, firstName, lastName, email);
        this.matricule = matricule;
        this.department = Department.valueOf(department);
    }

    public StudentDTO(Student student){
        super(student.getUserID(), student.getFirstName(), student.getLastName(), student.getEmail());
        this.matricule = student.getMatricule();
        this.department = student.getDepartment();
    }
}
