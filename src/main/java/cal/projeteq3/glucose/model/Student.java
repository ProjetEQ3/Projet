package cal.projeteq3.glucose.model;

import cal.projeteq3.glucose.dto.StudentDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public final class Student extends User<Student> {
    @OneToOne
    private CvFile cv;
    private String matricule;
    private Department department;

    public Student(long id, String firstName, String lastName, String email, String password, String matricule, String department, CvFile cvFile) {
        super(id, firstName, lastName, email, password);
        this.matricule = matricule;
        this.department = Department.valueOf(department);
        this.cv = cvFile;
    }

}
