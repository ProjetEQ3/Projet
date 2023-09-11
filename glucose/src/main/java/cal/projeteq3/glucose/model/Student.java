package cal.projeteq3.glucose.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Student extends User {
    @OneToOne
    private CvFile cv;
    private String matricule;
    private Department department;

    public Student(String firstName, String lastName, String email, String password, String matricule, String department, CvFile cvFile) {
        super(firstName, lastName, email, password);
        this.matricule = matricule;
        this.department = Department.valueOf(department);
        this.cv = cvFile;
    }
}
