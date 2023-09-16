package cal.projeteq3.glucose.dto.user;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.user.Student;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO extends UserDTO {
    private String matricule;
    private Department department;
    private CvFileDTO cvFile;

    @Builder
    public StudentDTO(
      Long id, String firstName, String lastName, String email, String role, String matricule, String department
    ){
        super(id, firstName, lastName, email, role);
        this.matricule = matricule;
        this.department = Department.valueOf(department);
    }

    public StudentDTO(Student student){
        super(
          student.getId(), student.getFirstName(), student.getLastName(), student.getEmail(),
          student.getCredentials().getRole().toString()
        );
        this.matricule = student.getMatricule();
        this.department = student.getDepartment();
        this.cvFile = student.getCvFile() == null ? null : new CvFileDTO(student.getCvFile());
    }

    public Student toEntity() {
        return Student.builder()
            .id(this.getId())
            .firstName(this.getFirstName())
            .lastName(this.getLastName())
            .email(this.getEmail())
            .matricule(this.getMatricule())
            .department(this.getDepartment().toString())
            .cvFile(this.getCvFile() == null ? null : this.getCvFile().toEntity())
            .build();
    }

}
