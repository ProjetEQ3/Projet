package cal.projeteq3.glucose.dto.user;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.user.Student;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO extends UserDTO {
    private String matricule;
    private Department department;
    private CvFileDTO cvFile;
    private List<Long> jobApplications;

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

    public StudentDTO(Student student, Long jobApplicationId) {
        super(
          student.getId(), student.getFirstName(), student.getLastName(), student.getEmail(),
          student.getCredentials().getRole().toString()
        );
        this.matricule = student.getMatricule();
        this.department = student.getDepartment();
        this.cvFile = student.getCvFile() == null ? null : new CvFileDTO(student.getCvFile());
        this.jobApplications.add(jobApplicationId);
    }

    public StudentDTO(String firstName, String lastName, String matricule, Department department){
        super(firstName, lastName, "STUDENT");
        this.matricule = matricule;
        this.department = department;
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
