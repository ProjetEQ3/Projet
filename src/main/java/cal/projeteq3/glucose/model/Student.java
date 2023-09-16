package cal.projeteq3.glucose.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User {
    @OneToOne
    private CvFile cv;
    private String matricule;
    private Department department;

    public Student(String firstName, String lastName, String email, String password, String matricule, String department, CvFile cvFile) {
        super(firstName, lastName, email, password);
        this.matricule = matricule;
        this.department = Department.valueOf(department);
        addCv(cvFile);
    }

    public void addCv(CvFile cvFile) {
        this.cv = cvFile;
        this.cv.setStudent(this);
    }

}
